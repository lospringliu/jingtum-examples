# -*- coding:utf-8 -*-
import sys 
sys.path.append("../")

from jingtumsdk.jingtumwallet import FinGate
from jingtumsdk.jingtumwallet import Wallet
from jingtumsdk.logger import logger
import time

#config just for test
api_address = "tapi.jingtum.com"
is_https = False
web_socket_address = "wss://tapi.jingtum.com:5443" 
api_version = "v2"

# api_address = "kapi.jingtum.com:443"
# is_https = True
# web_socket_address = "wss://kapi.jingtum.com:5443" 
# api_version = "v2"

# root account just for test
test_address = "jHb9CJAWyB4jr91VRWn96DkukG4bwdtyTh"
test_secret = "snoPBjXtMeMyMHUVTgbuqAfg1SUTb"
test_issuer = "jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS"

# ulimit account just for test
test_ulimit_address = "jJ8PzpT7er3tXEWaUsVTPy3kQUaHVHdxvp"
test_ulimit_secret = "shYK7gZVBzw4m71FFqZh9TWGXLR6Q"

# init FinGate
fingatehelper = FinGate()
fingatehelper.setServer(api_address, is_https, web_socket_address, api_version)
fingatehelper.setFinGate(test_address, test_secret)
fingatehelper.setActiveAmount(100000)
fingatehelper.setTrustLimit(100)

# create a wallet
ret = fingatehelper.createWallet()

my_address, my_secret = None, None
if ret.has_key("success") and ret["success"]:
    my_address, my_secret = ret["wallet"]["address"], ret["wallet"]["secret"]

# websocket init and subscribe
fingatehelper.connect()
fingatehelper.subscribe(my_address, my_secret)

# active wallet
ret = fingatehelper.activeWallet(my_address)

class WalletTest(Wallet):
    def __init__(self, address, secret, fingate=None):
        super(WalletTest, self).__init__(address, secret, fingate)
        self.wallet_status = 0
        
        self.last_order_hash = None
        self.last_resource_id = None

    def set_wallet_status(self, status):
        self.wallet_status = status

    def get_wallet_status(self):
        return self.wallet_status

    def set_last_order_hash(self, hash_id):
        self.last_order_hash = hash_id

    def set_last_resource_id(self, resource_id):
        self.last_resource_id = resource_id

    def on_ws_receive(self, data, *arg):
        logger.info("do_socket_receive0")

        if data.has_key("success") and data["success"]:
            if wallethelper and data.has_key("type") and data["type"] == "Payment":
                ret = wallethelper.getBalance()
                print "2333333", ret
                if self.get_wallet_status() == 0:
                    self.set_wallet_status(1)
                elif self.get_wallet_status() == 2:
                    self.set_wallet_status(12)
            elif data.has_key("type") and data["type"] == "OfferCreate":
                logger.info("offer created:" + str(data) + str(arg))

                # set last order hash for next test
                if data.has_key("transaction"):
                    self.set_last_order_hash(data["transaction"]["hash"])

                self.set_wallet_status(6)
            elif data.has_key("type") and data["type"] == "OfferCancel":
                logger.info("offer canceled:" + str(data) + str(arg))
                self.set_wallet_status(8)
            elif data.has_key("type") and data["type"] == "TrustSet":
                logger.info("trust seted:" + str(data) + str(arg))
                self.set_wallet_status(14)
            else:
                logger.info("do_socket_receive:" + str(data) + str(arg))

# init my Wallet, ulimit Wallet
wallethelper = WalletTest(my_address, my_secret, fingatehelper)
ulimit_wallethelper = Wallet(test_ulimit_address, test_ulimit_secret)

# register ws callback
fingatehelper.setTxHandler(wallethelper.on_ws_receive)

while 1:
    if wallethelper and wallethelper.isActivated:
        if wallethelper.get_wallet_status() == 1: # USD payment, from ulimit wallet to my wallet
            ret = ulimit_wallethelper.submitPayment("USD", 2, wallethelper.address, issuer=test_issuer)
            if ret.has_key("success") and ret["success"]:
                wallethelper.set_last_resource_id(ret["client_resource_id"])
            wallethelper.set_wallet_status(2)
        elif wallethelper.get_wallet_status() == 3:
            r = wallethelper.getPayment(wallethelper.last_resource_id)
            logger.info("get_payment test:" + str(r))
            r = wallethelper.getPaymentList(results_per_page=3, page=1)
            logger.info("get_payments test:" + str(r))    
            wallethelper.set_wallet_status(4)
        elif wallethelper.get_wallet_status() == 4:
            r = wallethelper.getPathList(test_ulimit_address, test_ulimit_secret, wallethelper.address, 
                "1.00", "USD", issuer=test_issuer)
            logger.info("get_paths test:" + str(r))
            
            wallethelper.createOrder("buy", "SWT", 5, "USD", 1, None, test_issuer)
            wallethelper.set_wallet_status(5)
        elif wallethelper.get_wallet_status() == 6:
            r = wallethelper.getOrderList()
            logger.info("get_account_orders test:" + str(r))
            r = wallethelper.cancelOrder(1)
            logger.info("cancel_order 1 test:" + str(r))
            wallethelper.set_wallet_status(7)
        elif wallethelper.get_wallet_status() == 8:
            if wallethelper.last_order_hash is not None:
                r = wallethelper.getOrder(wallethelper.last_order_hash)
                logger.info("get_order_by_hash test:" + str(r))
            wallethelper.set_wallet_status(9)
        elif wallethelper.get_wallet_status() == 9:
            if wallethelper.last_order_hash is not None:
                r = wallethelper.getTransaction(wallethelper.last_order_hash)
                logger.info("retrieve_order_transaction test:" + str(r))
            r = wallethelper.getTransactionList()
            #logger.info("order_transaction_history test:" + str(r))
            wallethelper.set_wallet_status(10)
        elif wallethelper.get_wallet_status() == 10:
            r = wallethelper.addRelation("authorize", test_address,
                limit_currency="USD", limit_issuer=test_issuer, limit_value=1)
            #r = wallethelper.addRelation("authorize", test_address)
            logger.info("add_relations test:" + str(r))
            time.sleep(8)
            try:
                r = wallethelper.getRelationList(relations_type="authorize", counterparty=test_address, 
                    currency="USD+"+test_issuer)
                #r = wallethelper.getRelationList()
                logger.info("get_relations test:" + str(r))
            except Exception, e:
                logger.error("get_relations:" + str(e)) 
            wallethelper.set_wallet_status(11)
        elif wallethelper.get_wallet_status() == 11:
            try:
                r = wallethelper.getCoRelationList(test_address, test_secret, "authorize", "USD+"+test_issuer)
                #r = wallethelper.getCoRelationList(test_address, test_secret)
                logger.info("get_counter_relations test:" + str(r))
            except Exception, e:
                logger.error("get_counter_relations:" + str(e)) 
            try:
                r = wallethelper.removeRelation("authorize", test_address, test_issuer, "USD", 1000)
                logger.info("delete_relations test:" + str(r))
            except Exception, e:
                logger.error("delete_relations:" + str(e)) 
            wallethelper.set_wallet_status(12)
        elif wallethelper.get_wallet_status() == 12:
            r = wallethelper.addTrustline("USD", counterparty=test_issuer)
            logger.info("post_trustline test:" + str(r))
            wallethelper.set_wallet_status(13)
        elif wallethelper.get_wallet_status() == 14:
            r = wallethelper.getTrustlineList()
            logger.info("get_trustlines test:" + str(r)) 
            wallethelper.set_wallet_status(15)


    time.sleep(2)
    pass