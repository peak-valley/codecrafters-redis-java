重构思路

使用一个Map，key存命令，value存command，拿到命令后，直接从map中取出command，以此实现可扩展
[replication-15] Running tests for Replication > Stage #15: ACKs with commands
[replication-15] Master is running on port 6379
[replication-15] $ ./spawn_redis_server.sh --port 6380 --replicaof localhost 6379
[your_program] send ping to master
[replication-15] ping received.
[replication-15] +PONG sent.
[your_program] +PONG
[your_program] send [REPLCONF, listening-port, 6380] to master
[replication-15] REPLCONF listening-port 6380 received.
[replication-15] +OK sent.
[your_program] +OK
[your_program] send [REPLCONF, capa, psync2] to master
[replication-15] REPLCONF capa psync2 received.
[replication-15] +OK sent.
[your_program] +OK
[your_program] send [PSYNC, ?, -1] to master
[replication-15] PSYNC ? -1 received.
[replication-15] +FULLRESYNC 38onpkf7ny9ue8zmunowuoe7qz4kj3mh439dyoxb 0 sent.
[replication-15] RDB file sent.
[replication-15] $ redis-cli REPLCONF GETACK *
[your_program] +FULLRESYNC 38onpkf7ny9ue8zmunowuoe7qz4kj3mh439dyoxb 0
[your_program] $88
[your_program] REDIS0011�	redis-ver7.2.0�
[your_program] redis-bits�@�ctime�m�e�used-mem°��aof-base���n;���Z�
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $3
[your_program] ACK
[your_program] $1
[your_program] 0
[your_program]
[replication-15] REPLCONF ACK 0 received.
[replication-15] $ redis-cli PING
[replication-15] $ redis-cli REPLCONF GETACK *
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[replication-15] Length mismatch between actual message and expected message.
[replication-15] Test failed
[replication-15] Terminating program
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $3
[your_program] ACK
[your_program] $2
[your_program] 30
[your_program]