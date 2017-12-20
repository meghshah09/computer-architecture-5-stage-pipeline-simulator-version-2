# computer-architecture-5-stage-pipeline-simulator-version-2

Incorporate an integer division unit (DU), pipelined into 4 stages (DU1 through
DU4), each with a delay of one cycle. The addition of this function unit will lead to out-of-order
completions and all relevant dependencies that may be violated by out-of-order completion need to be
handled correctly. When multiple function units contend to use the MEM stage, the priorities for using
the MEM stage are as follows: DU (highest), MULtiply FU, IntegerFU (lowest).
