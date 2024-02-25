重构思路

使用一个Map，key存命令，value存command，拿到命令后，直接从map中取出command，以此实现可扩展

[replication-10] Test passed.
[replication-10] Terminating program
[replication-10] Program terminated successfully
[replication-11] Running tests for Replication > Stage #11: Single-replica propagation
[replication-11] $ ./spawn_redis_server.sh --port 6379
[replication-11] $ redis-cli PING
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:53464
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:53472
[your_program] process read b:[*]
[your_program] multiBulk has 1 elements
[your_program] process read b:[$]
[your_program] PING command is running
[replication-11] PONG received.
[replication-11] $ redis-cli REPLCONF listening-port 6380
[your_program] build response $4
[your_program] PONG
[your_program]
[your_program] process read b:[*]
[your_program] multiBulk has 3 elements
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] REPLCONF command is running
[replication-11] OK received.
[replication-11] $ redis-cli PSYNC ? -1
[your_program] process read b:[*]
[your_program] multiBulk has 3 elements
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] PSYNC command is running
[replication-11] FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0 received.
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:53464
[replication-11] Successfully received RDB file from master.
[replication-11] Setting key foo to 123
[replication-11] $ redis-cli SET foo 123
[replication-11] Setting key bar to 456
[replication-11] $ redis-cli SET bar 456
[replication-11] Setting key baz to 789
[replication-11] $ redis-cli SET baz 789
[your_program] process read b:[*]
[your_program] multiBulk has 3 elements
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] SET command is running
[your_program] set command param:foo 123
[your_program] not slave node
[your_program] process read b:[*]
[your_program] multiBulk has 3 elements
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] SET command is running
[your_program] set command param:bar 456
[your_program] not slave node
[your_program] process read b:[*]
[your_program] multiBulk has 3 elements
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] process read b:[$]
[your_program] SET command is running
[your_program] set command param:baz 789
[your_program] not slave node
[replication-11] timed out, test exceeded 10 seconds
[replication-11] Test failed
[replication-11] Terminating program
[replication-11]