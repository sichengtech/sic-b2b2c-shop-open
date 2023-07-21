
# shop-junit-jacoco模块说明

shop-junit-jacoco模块是一个空模块，主要存放汇总的覆盖率报告。

# 目标
在maven的多模块项目中配置Jacoco插件，显示多个模块的单元测试覆盖率汇总报告

# 解决方法
使用jecoco的goal--report-aggregate ，这个goal是jacoco 0.7.7版本以后，专门为多模块覆盖率显示所设置，可以统计该模块所依赖的所有其他模块的覆盖率

# 问题描述 

maven多模块项目如果不使用report-aggregate， 结果是每一个模块生成了一个独立的单元测试覆盖率报告。

多个独立的覆盖率报告没有汇总，没有交叉计算，这样导致覆盖率低于实际值10倍，这不能忍受阿。
