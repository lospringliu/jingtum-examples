# -*- coding:utf-8 -*-
import sys 
sys.path.append("../")

from jingtumsdk.jingtumwallet import FinGate
from jingtumsdk.logger import logger
import time

ekey = "5ad650b05b161b3d3b50525b1f44dff4cccb65e0a5fdcd255002c922a1c30bd2"
custom = "JT00000028"

fingatehelper = FinGate()
fingatehelper.setTest()
fingatehelper.setConfig(custom, ekey)

order = fingatehelper.getNextUUID()

ret = fingatehelper.issueCustomTum(order, "USD", "123.45", "jNCHCnzc5yvUq3vW6UXw9hNscsk7C35BxU")
print "issueCustomTum", ret

ret = fingatehelper.queryIssue(order)
print "queryIssue", ret

ret = fingatehelper.queryCustomTum("USD")
print "queryCustomTum", ret


