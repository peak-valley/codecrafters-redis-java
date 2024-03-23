重构思路

使用一个Map，key存命令，value存command，拿到命令后，直接从map中取出command，以此实现可扩展

[streams-6] Running tests for Streams > Stage #6: Query entries from stream
[streams-6] $ ./spawn_redis_server.sh
[streams-6] $ redis-cli xadd "raspberry" "0-1" "foo bar"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:59232
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 56
[streams-6] Received response: ""0-1""
[streams-6] $ redis-cli xadd "raspberry" "0-2" "foo bar"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 56
[streams-6] Received response: ""0-2""
[streams-6] $ redis-cli xadd "raspberry" "0-3" "foo bar"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-3
[your_program]
[your_program] XADD offset add: 56
[streams-6] Received response: ""0-3""
[streams-6] $ redis-cli xadd "raspberry" "0-4" "foo bar"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-4
[your_program]
[your_program] XADD offset add: 56
[streams-6] Received response: ""0-4""
[streams-6] $ redis-cli xrange "raspberry" 0-2 "0-4"
[your_program] multiBulk has 4 elements
[your_program] XRANGE command is running
[your_program] build command ->*3
[your_program] *2
[your_program] $3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program]
[streams-6] redis: can't parse array reply: "3"
[streams-6] Test failed
[streams-6] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:47)
[your_program] 	at Main.lambda$main$0(Main.java:35)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[streams-6] Program terminated successfully