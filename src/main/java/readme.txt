重构思路

使用一个Map，key存命令，value存command，拿到命令后，直接从map中取出command，以此实现可扩展


[streams-11] Running tests for Streams > Stage #11: Blocking reads
[streams-11] $ ./spawn_redis_server.sh
[streams-11] $ redis-cli xadd "mango" "0-1" "temperature 86"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49668
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 60
[streams-11] Received response: ""0-1""
[streams-11] $ redis-cli xread block 'Ϩ' streams "mango 0-1"
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[your_program] build result->*1
[your_program] *2
[your_program] $3
[your_program] 0-1
[your_program] *2
[your_program] $11
[your_program] temperature
[your_program] $2
[your_program] 86
[your_program]
[streams-11] $ redis-cli xadd "mango" "0-2" "temperature 86"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 60
[streams-11] Received response: ""0-2""
[streams-11] Received response: "[
[streams-11]   {
[streams-11]     "Stream": "mango",
[streams-11]     "Messages": [
[streams-11]       {
[streams-11]         "ID": "0-1",
[streams-11]         "Values": {
[streams-11]           "temperature": "86"
[streams-11]         }
[streams-11]       }
[streams-11]     ]
[streams-11]   }
[streams-11] ]"
[streams-11] Expected [
[streams-11]   {
[streams-11]     "Stream": "mango",
[streams-11]     "Messages": [
[streams-11]       {
[streams-11]         "ID": "0-2",
[streams-11]         "Values": {
[streams-11]           "temperature": "86"
[streams-11]         }
[streams-11]       }
[streams-11]     ]
[streams-11]   }
[streams-11] ], got [
[streams-11]   {
[streams-11]     "Stream": "mango",
[streams-11]     "Messages": [
[streams-11]       {
[streams-11]         "ID": "0-1",
[streams-11]         "Values": {
[streams-11]           "temperature": "86"
[streams-11]         }
[streams-11]       }
[streams-11]     ]
[streams-11]   }
[streams-11] ]
[streams-11] Test failed
[streams-11] Terminating program
[streams-11] Program terminated successfully