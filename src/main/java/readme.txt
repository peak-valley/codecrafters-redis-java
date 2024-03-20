[streams-4] Running tests for Streams > Stage #4: Partially auto-generated IDs
[streams-4] $ ./spawn_redis_server.sh
[streams-4] $ redis-cli xadd "orange" "0-*" "foo bar"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:44736
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program] 
[your_program] XADD offset add: 53
[streams-4] Received response: ""0-1""
[streams-4] $ redis-cli xadd "orange" "1-*" "foo bar"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 1-2
[your_program] 
[your_program] XADD offset add: 53
[streams-4] Received response: ""1-2""
[streams-4] Expected "1-0", got "1-2"
[streams-4] Test failed
[streams-4] Terminating program
[streams-4] Program terminated successfully