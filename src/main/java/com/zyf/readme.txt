Build your own Redis
Build your own Redis
using Java
Introduction
Repository Setup
Bind to a port
Respond to PING
Respond to multiple PINGs
Handle concurrent clients
Implement the ECHO command
Implement the SET & GET commands
Expiry
Base stages complete!
Replication

Configure listening port
The INFO command
The INFO command on a replica
Initial Replication ID and Offset
Send handshake (1/3)
Send handshake (2/3)
Send handshake (3/3)
Receive handshake (1/2)
Receive handshake (2/2)
Empty RDB Transfer
Single-replica propagation
Multi Replica Command Propagation
Command Processing
ACKs with no commands
ACKs with commands
WAIT with no replicas
WAIT with no commands
WAIT with multiple commands
Extension complete!
RDB Persistence

RDB file config
Read a key
Read a string value
Read multiple keys
Read multiple string values
Read value with expiry
Extension complete!
Streams

The TYPE command
Create a stream
Validating entry IDs
Partially auto-generated IDs
Fully auto-generated IDs
Query entries from stream
Query with -
Query with +
Query single stream using XREAD
Query multiple streams using XREAD
Blocking reads
Blocking reads without timeout
Blocking reads using $
Extension complete!
Transactions

The INCR command (1/3)
The INCR command (2/3)
The INCR command (3/3)
The MULTI command
The EXEC command
Empty transaction
Queueing commands
Executing a transaction
The DISCARD command
Failures within transactions
Multiple transactions

Collapse
FREE THIS MONTH

system

light

dark
Upgrade
Queueing commands #rs9
In-progress
Instructions
Code Examples
Concepts
Forum
Test Runner:
Tests failed.

Hide Instructions
Tests failed. Click here to view logs.

To run tests again, make changes to your code and run the test command:

codecrafters cli
git

copy
codecrafters test # Visit https://codecrafters.io/cli to install
We recommend using the CodeCrafters CLI, but you can use Git too.

Your Task
In-progress
Share Feedback
Medium
In this stage, you'll add support for queuing commands within a transaction.

Queuing commands
After MULTI is executed, any further commands from a connection are queued until EXEC is executed.

The response to all of these commands is +QUEUED\r\n (That's QUEUED encoded as a Simple String).

Example:

$ redis-cli
> MULTI
OK
> SET foo 41
QUEUED
> INCR foo
QUEUED

... (and so on, until EXEC is executed)
When commands are queued, they should not be executed or alter the database in any way.

In the example above, until EXEC is executed, the key foo will not exist.

Tests
The tester will execute your program like this:

$ ./your_program.sh
The tester will then connect to your server as a Redis client, and send multiple commands using the same connection:

$ redis-cli
> MULTI
> SET foo 41 (expecting "+QUEUED\r\n")
> INCR foo (expecting "+QUEUED\r\n")
Since these commands were only "queued", the key foo should not exist yet. The tester will verify this by creating another connection and sending this command:

$ redis-cli GET foo (expecting `$-1\r\n` as the response)

View Code Examples

Collapse
Hints
Filter by Java

avatar
Write
Preview
Found an interesting resource? Share it with the community.
Markdown supported.
Comment

RECENT ATTEMPTS
11
avatar
inner
C#
55 / 55
avatar
Aimer101
Kotlin
55 / 55
avatar
ValentinJub
Go
55 / 55
avatar
czl9707
Python
55 / 55
avatar
corco
Rust
55 / 55
avatar
peak-valley
Java
50 / 55
avatar
jeenolentin
Python
30 / 55
avatar
GuilhermeGcaires
Rust
14 / 55
avatar
andy9-eng
Python
8 / 55
avatar
fffive
Go
4 / 55
avatar
renshou753
Go
4 / 55
Invite a friend
Logs
[compile] [INFO] Scanning for projects...
[compile] [INFO]
[compile] [INFO] ----------------< io.codecrafters:build-your-own-redis >----------------
[compile] [INFO] Building build-your-own-redis 1.0
[compile] [INFO]   from pom.xml
[compile] [INFO] --------------------------------[ jar ]---------------------------------
[compile] [INFO]
[compile] [INFO] --- resources:3.3.1:resources (default-resources) @ build-your-own-redis ---
[compile] [INFO] skip non existing resourceDirectory /app/src/main/resources
[compile] [INFO]
[compile] [INFO] --- compiler:3.11.0:compile (default-compile) @ build-your-own-redis ---
[compile] [INFO] Changes detected - recompiling the module! :source
[compile] [INFO] Compiling 52 source files with javac [debug target 21] to target/classes
[compile] [INFO] Annotation processing is enabled because one or more processors were found
[compile]   on the class path. A future release of javac may disable annotation processing
[compile]   unless at least one processor is specified by name (-processor), or a search
[compile]   path is specified (--processor-path, --processor-module-path), or annotation
[compile]   processing is enabled explicitly (-proc:only, -proc:full).
[compile]   Use -Xlint:-options to suppress this message.
[compile]   Use -proc:none to disable annotation processing.
[compile] [INFO] /app/src/main/java/com/zyf/handle/AbstractHandler.java: Some input files use unchecked or unsafe operations.
[compile] [INFO] /app/src/main/java/com/zyf/handle/AbstractHandler.java: Recompile with -Xlint:unchecked for details.
[compile] [INFO]
[compile] [INFO] --- resources:3.3.1:testResources (default-testResources) @ build-your-own-redis ---
[compile] [INFO] skip non existing resourceDirectory /app/src/test/resources
[compile] [INFO]
[compile] [INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ build-your-own-redis ---
[compile] [INFO] No sources to compile
[compile] [INFO]
[compile] [INFO] --- surefire:3.1.2:test (default-test) @ build-your-own-redis ---
[compile] [INFO] No tests to run.
[compile] [INFO]
[compile] [INFO] --- jar:3.3.0:jar (default-jar) @ build-your-own-redis ---
[compile] [INFO] Building jar: /app/target/build-your-own-redis-1.0.jar
[compile] [INFO]
[compile] [INFO] --- assembly:3.6.0:single (make-assembly) @ build-your-own-redis ---
[compile] [INFO] Building jar: /tmp/codecrafters-build-redis-java/java_redis.jar
[compile] [WARNING] Configuration option 'appendAssemblyId' is set to false.
[compile] Instead of attaching the assembly file: /tmp/codecrafters-build-redis-java/java_redis.jar, it will become the file for main project artifact.
[compile] NOTE: If multiple descriptors or descriptor-formats are provided for this project, the value of this file will be non-deterministic!
[compile] [WARNING] Replacing pre-existing project main-artifact file: /app/target/build-your-own-redis-1.0.jar
[compile] with assembly file: /tmp/codecrafters-build-redis-java/java_redis.jar
[compile] [INFO] ------------------------------------------------------------------------
[compile] [INFO] BUILD SUCCESS
[compile] [INFO] ------------------------------------------------------------------------
[compile] [INFO] Total time:  8.200 s
[compile] [INFO] Finished at: 2024-11-11T01:49:33Z
[compile] [INFO] ------------------------------------------------------------------------
[compile] Compilation successful.
Debug = true
[tester::#RS9] Running tests for Stage #RS9 (Transactions - Queueing commands)
[tester::#RS9] $ ./spawn_redis_server.sh
[tester::#RS9] client-1: $ redis-cli MULTI
[tester::#RS9] client-1: Sent bytes: "*1\r\n$5\r\nMULTI\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:35972
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:35986
[your_program] multiBulk has 1 elements
[your_program] MULTI command is running
[tester::#RS9] client-1: Received bytes: "+OK\r\n"
[tester::#RS9] client-1: Received RESP simple string: "OK"
[tester::#RS9] Received "OK"
[tester::#RS9] Sending command: #1/#2
[tester::#RS9] client-1: > SET banana 66
[tester::#RS9] client-1: Sent bytes: "*3\r\n$3\r\nSET\r\n$6\r\nbanana\r\n$2\r\n66\r\n"
[your_program] multiBulk has 3 elements
[your_program] SET offset add: 33
[tester::#RS9] client-1: Received bytes: "+QUEUED\r\n"
[tester::#RS9] client-1: Received RESP simple string: "QUEUED"
[tester::#RS9] Received "QUEUED"
[tester::#RS9] Sending command: #2/#2
[tester::#RS9] client-1: > INCR banana
[tester::#RS9] client-1: Sent bytes: "*2\r\n$4\r\nINCR\r\n$6\r\nbanana\r\n"
[your_program] multiBulk has 2 elements
[your_program] INCR offset add: 26
[tester::#RS9] client-1: Received bytes: "+QUEUED\r\n"
[tester::#RS9] client-1: Received RESP simple string: "QUEUED"
[tester::#RS9] Received "QUEUED"
[tester::#RS9] client-2: $ redis-cli GET banana
[tester::#RS9] client-2: Sent bytes: "*2\r\n$3\r\nGET\r\n$6\r\nbanana\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:banana
[your_program] value is null, return $-1
[your_program]
[tester::#RS9] client-2: Received bytes: "$-1\r\n"
[tester::#RS9] client-2: Received RESP null bulk string: "$-1\r\n"
[tester::#RS9] Received "$-1\r\n"
[tester::#RS9] Test passed.
[tester::#RS9] Terminating program
[your_program] Exception in thread "Thread-0" Exception in thread "Thread-1" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#RS9] Program terminated successfully
[tester::#WE1] Running tests for Stage #WE1 (Transactions - Empty transaction)
[tester::#WE1] $ ./spawn_redis_server.sh
[tester::#WE1] client: $ redis-cli MULTI
[tester::#WE1] client: Sent bytes: "*1\r\n$5\r\nMULTI\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36006
[your_program] multiBulk has 1 elements
[your_program] MULTI command is running
[tester::#WE1] client: Received bytes: "+OK\r\n"
[tester::#WE1] client: Received RESP simple string: "OK"
[tester::#WE1] Received "OK"
[tester::#WE1] client: > EXEC
[tester::#WE1] client: Sent bytes: "*1\r\n$4\r\nEXEC\r\n"
[your_program] multiBulk has 1 elements
[your_program] EXEC command is running
[your_program] 01:49:34.249 [Thread-0] INFO com.zyf.commands.Exec -- multi state:true
[your_program] build command ->*0
[your_program]
[tester::#WE1] client: Received bytes: "*0\r\n"
[tester::#WE1] client: Received RESP array: []
[tester::#WE1] Received []
[tester::#WE1] client: > EXEC
[tester::#WE1] client: Sent bytes: "*1\r\n$4\r\nEXEC\r\n"
[your_program] multiBulk has 1 elements
[your_program] EXEC command is running
[your_program] 01:49:34.260 [Thread-0] INFO com.zyf.commands.Exec -- multi state:false
[tester::#WE1] client: Received bytes: "-ERR EXEC without MULTI\r\n"
[tester::#WE1] client: Received RESP error: "ERR: ERR EXEC without MULTI"
[tester::#WE1] Received "ERR: ERR EXEC without MULTI"
[tester::#WE1] Test passed.
[tester::#WE1] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#WE1] Program terminated successfully
[tester::#LO4] Running tests for Stage #LO4 (Transactions - The EXEC command)
[tester::#LO4] $ ./spawn_redis_server.sh
[tester::#LO4] client: $ redis-cli EXEC
[tester::#LO4] client: Sent bytes: "*1\r\n$4\r\nEXEC\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36032
[your_program] multiBulk has 1 elements
[your_program] EXEC command is running
[your_program] 01:49:34.895 [Thread-0] INFO com.zyf.commands.Exec -- multi state:false
[tester::#LO4] client: Received bytes: "-ERR EXEC without MULTI\r\n"
[tester::#LO4] client: Received RESP error: "ERR: ERR EXEC without MULTI"
[tester::#LO4] Received "ERR: ERR EXEC without MULTI"
[tester::#LO4] Test passed.
[tester::#LO4] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#LO4] Program terminated successfully
[tester::#PN0] Running tests for Stage #PN0 (Transactions - The MULTI command)
[tester::#PN0] $ ./spawn_redis_server.sh
[tester::#PN0] client: $ redis-cli MULTI
[tester::#PN0] client: Sent bytes: "*1\r\n$5\r\nMULTI\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36048
[your_program] multiBulk has 1 elements
[your_program] MULTI command is running
[tester::#PN0] client: Received bytes: "+OK\r\n"
[tester::#PN0] client: Received RESP simple string: "OK"
[tester::#PN0] Received "OK"
[tester::#PN0] Test passed.
[tester::#PN0] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#PN0] Program terminated successfully
[tester::#MK1] Running tests for Stage #MK1 (Transactions - The INCR command (3/3))
[tester::#MK1] $ ./spawn_redis_server.sh
[tester::#MK1] client: $ redis-cli SET blueberry mango
[tester::#MK1] client: Sent bytes: "*3\r\n$3\r\nSET\r\n$9\r\nblueberry\r\n$5\r\nmango\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36060
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:blueberry mango
[your_program] SET offset add: 39
[tester::#MK1] client: Received bytes: "+OK\r\n"
[tester::#MK1] client: Received RESP simple string: "OK"
[tester::#MK1] Received "OK"
[tester::#MK1] client: > INCR blueberry
[tester::#MK1] client: Sent bytes: "*2\r\n$4\r\nINCR\r\n$9\r\nblueberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] INCR command is running
[your_program] 01:49:36.180 [Thread-0] INFO com.zyf.commands.Increment -- incr:key:blueberry
[your_program] INCR offset add: 29
[tester::#MK1] client: Received bytes: "-ERR value is not an integer or out of range\r\n"
[tester::#MK1] client: Received RESP error: "ERR: ERR value is not an integer or out of range"
[tester::#MK1] Received "ERR: ERR value is not an integer or out of range"
[tester::#MK1] Test passed.
[tester::#MK1] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#MK1] Program terminated successfully
[tester::#LZ8] Running tests for Stage #LZ8 (Transactions - The INCR command (2/3))
[tester::#LZ8] $ ./spawn_redis_server.sh
[tester::#LZ8] client: $ redis-cli INCR raspberry
[tester::#LZ8] client: Sent bytes: "*2\r\n$4\r\nINCR\r\n$9\r\nraspberry\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36082
[your_program] multiBulk has 2 elements
[your_program] INCR command is running
[your_program] 01:49:36.800 [Thread-0] INFO com.zyf.commands.Increment -- incr:key:raspberry
[your_program] INCR offset add: 29
[tester::#LZ8] client: Received bytes: ":1\r\n"
[tester::#LZ8] client: Received RESP integer: 1
[tester::#LZ8] Received 1
[tester::#LZ8] client: > INCR raspberry
[tester::#LZ8] client: Sent bytes: "*2\r\n$4\r\nINCR\r\n$9\r\nraspberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] INCR command is running
[your_program] 01:49:36.818 [Thread-0] INFO com.zyf.commands.Increment -- incr:key:raspberry
[your_program] INCR offset add: 29
[tester::#LZ8] client: Received bytes: ":2\r\n"
[tester::#LZ8] client: Received RESP integer: 2
[tester::#LZ8] Received 2
[tester::#LZ8] client: > GET raspberry
[tester::#LZ8] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$9\r\nraspberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:raspberry
[your_program] value is 2
[your_program] build response $1
[your_program] 2
[your_program]
[tester::#LZ8] client: Received bytes: "$1\r\n2\r\n"
[tester::#LZ8] client: Received RESP bulk string: "2"
[tester::#LZ8] Received "2"
[tester::#LZ8] Test passed.
[tester::#LZ8] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#LZ8] Program terminated successfully
[tester::#SI4] Running tests for Stage #SI4 (Transactions - The INCR command (1/3))
[tester::#SI4] $ ./spawn_redis_server.sh
[tester::#SI4] client: $ redis-cli SET blueberry 9
[tester::#SI4] client: Sent bytes: "*3\r\n$3\r\nSET\r\n$9\r\nblueberry\r\n$1\r\n9\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36098
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:blueberry 9
[your_program] SET offset add: 35
[tester::#SI4] client: Received bytes: "+OK\r\n"
[tester::#SI4] client: Received RESP simple string: "OK"
[tester::#SI4] Received "OK"
[tester::#SI4] client: > INCR blueberry
[tester::#SI4] client: Sent bytes: "*2\r\n$4\r\nINCR\r\n$9\r\nblueberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] INCR command is running
[your_program] 01:49:37.557 [Thread-0] INFO com.zyf.commands.Increment -- incr:key:blueberry
[your_program] INCR offset add: 29
[tester::#SI4] client: Received bytes: ":10\r\n"
[tester::#SI4] client: Received RESP integer: 10
[tester::#SI4] Received 10
[tester::#SI4] Test passed.
[tester::#SI4] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#SI4] Program terminated successfully
[tester::#XU1] Running tests for Stage #XU1 (Streams - Blocking reads using $)
[tester::#XU1] $ ./spawn_redis_server.sh
[tester::#XU1] $ redis-cli xadd strawberry 0-1 temperature 74
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36128
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 66
[tester::#XU1] Received response: "0-1"
[tester::#XU1] $ redis-cli xread block 0 streams strawberry 0-1
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[tester::#XU1] $ redis-cli xadd strawberry 0-2 temperature 74
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36130
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 66
[tester::#XU1] Received response: "0-2"
[tester::#XU1] Received response: "[
[tester::#XU1]   {
[tester::#XU1]     "Stream": "strawberry",
[tester::#XU1]     "Messages": [
[tester::#XU1]       {
[tester::#XU1]         "ID": "0-2",
[tester::#XU1]         "Values": {
[tester::#XU1]           "temperature": "74"
[tester::#XU1]         }
[tester::#XU1]       }
[tester::#XU1]     ]
[tester::#XU1]   }
[tester::#XU1] ]"
[tester::#XU1] Test passed.
[tester::#XU1] Terminating program
[tester::#XU1] Program terminated successfully
[tester::#HW1] Running tests for Stage #HW1 (Streams - Blocking reads without timeout)
[tester::#HW1] $ ./spawn_redis_server.sh
[tester::#HW1] $ redis-cli xadd blueberry 0-1 temperature 66
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36152
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 64
[tester::#HW1] Received response: "0-1"
[tester::#HW1] $ redis-cli xread block 0 streams blueberry 0-1
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[tester::#HW1] $ redis-cli xadd blueberry 0-2 temperature 66
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36168
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 64
[tester::#HW1] Received response: "0-2"
[tester::#HW1] Received response: "[
[tester::#HW1]   {
[tester::#HW1]     "Stream": "blueberry",
[tester::#HW1]     "Messages": [
[tester::#HW1]       {
[tester::#HW1]         "ID": "0-2",
[tester::#HW1]         "Values": {
[tester::#HW1]           "temperature": "66"
[tester::#HW1]         }
[tester::#HW1]       }
[tester::#HW1]     ]
[tester::#HW1]   }
[tester::#HW1] ]"
[tester::#HW1] Test passed.
[tester::#HW1] Terminating program
[tester::#HW1] Program terminated successfully
[tester::#BS1] Running tests for Stage #BS1 (Streams - Blocking reads)
[tester::#BS1] $ ./spawn_redis_server.sh
[tester::#BS1] $ redis-cli xadd apple 0-1 temperature 66
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36194
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 60
[tester::#BS1] Received response: "0-1"
[tester::#BS1] $ redis-cli xread block 1000 streams apple 0-1
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[tester::#BS1] $ redis-cli xadd apple 0-2 temperature 66
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:36202
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 60
[tester::#BS1] Received response: "0-2"
[tester::#BS1] Received response: "[
[tester::#BS1]   {
[tester::#BS1]     "Stream": "apple",
[tester::#BS1]     "Messages": [
[tester::#BS1]       {
[tester::#BS1]         "ID": "0-2",
[tester::#BS1]         "Values": {
[tester::#BS1]           "temperature": "66"
[tester::#BS1]         }
[tester::#BS1]       }
[tester::#BS1]     ]
[tester::#BS1]   }
[tester::#BS1] ]"
[tester::#BS1] $ redis-cli xread block 1000 streams apple 0-2
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[your_program] time out XRead,data is null
[tester::#BS1] Received nil response
[tester::#BS1] Test passed.
[tester::#BS1] Terminating program
[tester::#BS1] Program terminated successfully
[tester::#RU9] Running tests for Stage #RU9 (Streams - Query multiple streams using XREAD)
[tester::#RU9] $ ./spawn_redis_server.sh
[tester::#RU9] $ redis-cli xadd pear 0-1 temperature 54
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60564
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 59
[tester::#RU9] Received response: "0-1"
[tester::#RU9] $ redis-cli xadd orange 0-2 humidity 63
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 57
[tester::#RU9] Received response: "0-2"
[tester::#RU9] $ redis-cli xread streams pear orange 0-0 0-1
[your_program] multiBulk has 6 elements
[your_program] XREAD command is running
[tester::#RU9] Received response: "[
[tester::#RU9]   {
[tester::#RU9]     "Stream": "pear",
[tester::#RU9]     "Messages": [
[tester::#RU9]       {
[tester::#RU9]         "ID": "0-1",
[tester::#RU9]         "Values": {
[tester::#RU9]           "temperature": "54"
[tester::#RU9]         }
[tester::#RU9]       }
[tester::#RU9]     ]
[tester::#RU9]   },
[tester::#RU9]   {
[tester::#RU9]     "Stream": "orange",
[tester::#RU9]     "Messages": [
[tester::#RU9]       {
[tester::#RU9]         "ID": "0-2",
[tester::#RU9]         "Values": {
[tester::#RU9]           "humidity": "63"
[tester::#RU9]         }
[tester::#RU9]       }
[tester::#RU9]     ]
[tester::#RU9]   }
[tester::#RU9] ]"
[tester::#RU9] Test passed.
[tester::#RU9] Terminating program
[tester::#RU9] Program terminated successfully
[tester::#UM0] Running tests for Stage #UM0 (Streams - Query single stream using XREAD)
[tester::#UM0] $ ./spawn_redis_server.sh
[tester::#UM0] $ redis-cli xadd strawberry 0-1 temperature 96
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60594
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 66
[tester::#UM0] Received response: "0-1"
[tester::#UM0] $ redis-cli xread streams strawberry 0-0
[your_program] multiBulk has 4 elements
[your_program] XREAD command is running
[tester::#UM0] Received response: "[
[tester::#UM0]   {
[tester::#UM0]     "Stream": "strawberry",
[tester::#UM0]     "Messages": [
[tester::#UM0]       {
[tester::#UM0]         "ID": "0-1",
[tester::#UM0]         "Values": {
[tester::#UM0]           "temperature": "96"
[tester::#UM0]         }
[tester::#UM0]       }
[tester::#UM0]     ]
[tester::#UM0]   }
[tester::#UM0] ]"
[tester::#UM0] Test passed.
[tester::#UM0] Terminating program
[tester::#UM0] Program terminated successfully
[tester::#FS1] Running tests for Stage #FS1 (Streams - Query with +)
[tester::#FS1] $ ./spawn_redis_server.sh
[tester::#FS1] $ redis-cli xadd blueberry 0-1 foo bar
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60612
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 56
[tester::#FS1] Received response: "0-1"
[tester::#FS1] $ redis-cli xadd blueberry 0-2 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 56
[tester::#FS1] Received response: "0-2"
[tester::#FS1] $ redis-cli xadd blueberry 0-3 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-3
[your_program]
[your_program] XADD offset add: 56
[tester::#FS1] Received response: "0-3"
[tester::#FS1] $ redis-cli xrange blueberry 0-2 +
[your_program] multiBulk has 4 elements
[your_program] XRANGE command is running
[your_program] build command ->*2
[your_program] *2
[your_program] $3
[your_program] 0-2
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] 0-3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program]
[tester::#FS1] Received response: "[
[tester::#FS1]   {
[tester::#FS1]     "ID": "0-2",
[tester::#FS1]     "Values": {
[tester::#FS1]       "foo": "bar"
[tester::#FS1]     }
[tester::#FS1]   },
[tester::#FS1]   {
[tester::#FS1]     "ID": "0-3",
[tester::#FS1]     "Values": {
[tester::#FS1]       "foo": "bar"
[tester::#FS1]     }
[tester::#FS1]   }
[tester::#FS1] ]"
[tester::#FS1] Test passed.
[tester::#FS1] Terminating program
[tester::#FS1] Program terminated successfully
[tester::#YP1] Running tests for Stage #YP1 (Streams - Query with -)
[tester::#YP1] $ ./spawn_redis_server.sh
[tester::#YP1] $ redis-cli xadd mango 0-1 foo bar
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60636
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 52
[tester::#YP1] Received response: "0-1"
[tester::#YP1] $ redis-cli xadd mango 0-2 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 52
[tester::#YP1] Received response: "0-2"
[tester::#YP1] $ redis-cli xadd mango 0-3 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-3
[your_program]
[your_program] XADD offset add: 52
[tester::#YP1] Received response: "0-3"
[tester::#YP1] $ redis-cli xadd mango 0-4 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-4
[your_program]
[your_program] XADD offset add: 52
[tester::#YP1] Received response: "0-4"
[tester::#YP1] $ redis-cli xrange mango - 0-3
[your_program] multiBulk has 4 elements
[your_program] XRANGE command is running
[your_program] build command ->*3
[your_program] *2
[your_program] $3
[your_program] 0-1
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] 0-2
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] 0-3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program]
[tester::#YP1] Received response: "[
[tester::#YP1]   {
[tester::#YP1]     "ID": "0-1",
[tester::#YP1]     "Values": {
[tester::#YP1]       "foo": "bar"
[tester::#YP1]     }
[tester::#YP1]   },
[tester::#YP1]   {
[tester::#YP1]     "ID": "0-2",
[tester::#YP1]     "Values": {
[tester::#YP1]       "foo": "bar"
[tester::#YP1]     }
[tester::#YP1]   },
[tester::#YP1]   {
[tester::#YP1]     "ID": "0-3",
[tester::#YP1]     "Values": {
[tester::#YP1]       "foo": "bar"
[tester::#YP1]     }
[tester::#YP1]   }
[tester::#YP1] ]"
[tester::#YP1] Test passed.
[tester::#YP1] Terminating program
[tester::#YP1] Program terminated successfully
[tester::#ZX1] Running tests for Stage #ZX1 (Streams - Query entries from stream)
[tester::#ZX1] $ ./spawn_redis_server.sh
[tester::#ZX1] $ redis-cli xadd strawberry 0-1 foo bar
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60654
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 58
[tester::#ZX1] Received response: "0-1"
[tester::#ZX1] $ redis-cli xadd strawberry 0-2 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-2
[your_program]
[your_program] XADD offset add: 58
[tester::#ZX1] Received response: "0-2"
[tester::#ZX1] $ redis-cli xadd strawberry 0-3 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-3
[your_program]
[your_program] XADD offset add: 58
[tester::#ZX1] Received response: "0-3"
[tester::#ZX1] $ redis-cli xadd strawberry 0-4 foo bar
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-4
[your_program]
[your_program] XADD offset add: 58
[tester::#ZX1] Received response: "0-4"
[tester::#ZX1] $ redis-cli xrange strawberry 0-2 0-4
[your_program] multiBulk has 4 elements
[your_program] XRANGE command is running
[your_program] build command ->*3
[your_program] *2
[your_program] $3
[your_program] 0-2
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] 0-3
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program] *2
[your_program] $3
[your_program] 0-4
[your_program] *2
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] bar
[your_program]
[tester::#ZX1] Received response: "[
[tester::#ZX1]   {
[tester::#ZX1]     "ID": "0-2",
[tester::#ZX1]     "Values": {
[tester::#ZX1]       "foo": "bar"
[tester::#ZX1]     }
[tester::#ZX1]   },
[tester::#ZX1]   {
[tester::#ZX1]     "ID": "0-3",
[tester::#ZX1]     "Values": {
[tester::#ZX1]       "foo": "bar"
[tester::#ZX1]     }
[tester::#ZX1]   },
[tester::#ZX1]   {
[tester::#ZX1]     "ID": "0-4",
[tester::#ZX1]     "Values": {
[tester::#ZX1]       "foo": "bar"
[tester::#ZX1]     }
[tester::#ZX1]   }
[tester::#ZX1] ]"
[tester::#ZX1] Test passed.
[tester::#ZX1] Terminating program
[tester::#ZX1] Program terminated successfully
[tester::#XU6] Running tests for Stage #XU6 (Streams - Fully auto-generated IDs)
[tester::#XU6] $ ./spawn_redis_server.sh
[tester::#XU6] client: $ redis-cli XADD grape * foo bar
[tester::#XU6] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$5\r\ngrape\r\n$1\r\n*\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60658
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $15
[your_program] 1731289786924-0
[your_program]
[your_program] XADD offset add: 50
[tester::#XU6] client: Received bytes: "$15\r\n1731289786924-0\r\n"
[tester::#XU6] client: Received RESP bulk string: "1731289786924-0"
[tester::#XU6] Received "1731289786924-0"
[tester::#XU6] The first part of the ID is a valid unix milliseconds timestamp
[tester::#XU6] The second part of the ID is a valid sequence number
[tester::#XU6] Test passed.
[tester::#XU6] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#XU6] Program terminated successfully
[tester::#YH3] Running tests for Stage #YH3 (Streams - Partially auto-generated IDs)
[tester::#YH3] $ ./spawn_redis_server.sh
[tester::#YH3] client: $ redis-cli XADD pear 0-* blueberry strawberry
[tester::#YH3] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$4\r\npear\r\n$3\r\n0-*\r\n$9\r\nblueberry\r\n$10\r\nstrawberry\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60672
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 65
[tester::#YH3] client: Received bytes: "$3\r\n0-1\r\n"
[tester::#YH3] client: Received RESP bulk string: "0-1"
[tester::#YH3] Received "0-1"
[tester::#YH3] client: > XADD pear 1-* blueberry strawberry
[tester::#YH3] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$4\r\npear\r\n$3\r\n1-*\r\n$9\r\nblueberry\r\n$10\r\nstrawberry\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 1-0
[your_program]
[your_program] XADD offset add: 65
[tester::#YH3] client: Received bytes: "$3\r\n1-0\r\n"
[tester::#YH3] client: Received RESP bulk string: "1-0"
[tester::#YH3] Received "1-0"
[tester::#YH3] client: > XADD pear 1-* strawberry orange
[tester::#YH3] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$4\r\npear\r\n$3\r\n1-*\r\n$10\r\nstrawberry\r\n$6\r\norange\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 1-1
[your_program]
[your_program] XADD offset add: 62
[tester::#YH3] client: Received bytes: "$3\r\n1-1\r\n"
[tester::#YH3] client: Received RESP bulk string: "1-1"
[tester::#YH3] Received "1-1"
[tester::#YH3] Test passed.
[tester::#YH3] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#YH3] Program terminated successfully
[tester::#HQ8] Running tests for Stage #HQ8 (Streams - Validating entry IDs)
[tester::#HQ8] $ ./spawn_redis_server.sh
[tester::#HQ8] client: $ redis-cli XADD pineapple 1-1 pineapple apple
[tester::#HQ8] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$9\r\npineapple\r\n$3\r\n1-1\r\n$9\r\npineapple\r\n$5\r\napple\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60694
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 1-1
[your_program]
[your_program] XADD offset add: 64
[tester::#HQ8] client: Received bytes: "$3\r\n1-1\r\n"
[tester::#HQ8] client: Received RESP bulk string: "1-1"
[tester::#HQ8] Received "1-1"
[tester::#HQ8] client: > XADD pineapple 1-2 banana orange
[tester::#HQ8] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$9\r\npineapple\r\n$3\r\n1-2\r\n$6\r\nbanana\r\n$6\r\norange\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 1-2
[your_program]
[your_program] XADD offset add: 62
[tester::#HQ8] client: Received bytes: "$3\r\n1-2\r\n"
[tester::#HQ8] client: Received RESP bulk string: "1-2"
[tester::#HQ8] Received "1-2"
[tester::#HQ8] client: > XADD pineapple 1-2 strawberry mango
[tester::#HQ8] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$9\r\npineapple\r\n$3\r\n1-2\r\n$10\r\nstrawberry\r\n$5\r\nmango\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] XADD offset add: 66
[tester::#HQ8] client: Received bytes: "-ERR The ID specified in XADD is equal or smaller than the target stream top item\r\n"
[tester::#HQ8] client: Received RESP error: "ERR: ERR The ID specified in XADD is equal or smaller than the target stream top item"
[tester::#HQ8] Received "ERR: ERR The ID specified in XADD is equal or smaller than the target stream top item"
[tester::#HQ8] client: > XADD pineapple 0-3 pear raspberry
[tester::#HQ8] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$9\r\npineapple\r\n$3\r\n0-3\r\n$4\r\npear\r\n$9\r\nraspberry\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] XADD offset add: 63
[tester::#HQ8] client: Received bytes: "-ERR The ID specified in XADD is equal or smaller than the target stream top item\r\n"
[tester::#HQ8] client: Received RESP error: "ERR: ERR The ID specified in XADD is equal or smaller than the target stream top item"
[tester::#HQ8] Received "ERR: ERR The ID specified in XADD is equal or smaller than the target stream top item"
[tester::#HQ8] client: > XADD pineapple 0-0 blueberry grape
[tester::#HQ8] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$9\r\npineapple\r\n$3\r\n0-0\r\n$9\r\nblueberry\r\n$5\r\ngrape\r\n"
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] XADD offset add: 64
[tester::#HQ8] client: Received bytes: "-ERR The ID specified in XADD must be greater than 0-0\r\n"
[tester::#HQ8] client: Received RESP error: "ERR: ERR The ID specified in XADD must be greater than 0-0"
[tester::#HQ8] Received "ERR: ERR The ID specified in XADD must be greater than 0-0"
[tester::#HQ8] Test passed.
[tester::#HQ8] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#HQ8] Program terminated successfully
[tester::#CF6] Running tests for Stage #CF6 (Streams - Create a stream)
[tester::#CF6] $ ./spawn_redis_server.sh
[tester::#CF6] client: $ redis-cli XADD banana 0-1 foo bar
[tester::#CF6] client: Sent bytes: "*5\r\n$4\r\nXADD\r\n$6\r\nbanana\r\n$3\r\n0-1\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60714
[your_program] multiBulk has 5 elements
[your_program] XADD command is running
[your_program] build response $3
[your_program] 0-1
[your_program]
[your_program] XADD offset add: 53
[tester::#CF6] client: Received bytes: "$3\r\n0-1\r\n"
[tester::#CF6] client: Received RESP bulk string: "0-1"
[tester::#CF6] Received "0-1"
[tester::#CF6] client: > TYPE banana
[tester::#CF6] client: Sent bytes: "*2\r\n$4\r\nTYPE\r\n$6\r\nbanana\r\n"
[your_program] multiBulk has 2 elements
[your_program] TYPE command is running
[tester::#CF6] client: Received bytes: "+stream\r\n"
[tester::#CF6] client: Received RESP simple string: "stream"
[tester::#CF6] Received "stream"
[tester::#CF6] Test passed.
[tester::#CF6] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#CF6] Program terminated successfully
[tester::#CC3] Running tests for Stage #CC3 (Streams - The TYPE command)
[tester::#CC3] $ ./spawn_redis_server.sh
[tester::#CC3] client: $ redis-cli SET strawberry pineapple
[tester::#CC3] client: Sent bytes: "*3\r\n$3\r\nSET\r\n$10\r\nstrawberry\r\n$9\r\npineapple\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60728
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:strawberry pineapple
[your_program] SET offset add: 45
[tester::#CC3] client: Received bytes: "+OK\r\n"
[tester::#CC3] client: Received RESP simple string: "OK"
[tester::#CC3] Received "OK"
[tester::#CC3] client: > TYPE strawberry
[tester::#CC3] client: Sent bytes: "*2\r\n$4\r\nTYPE\r\n$10\r\nstrawberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] TYPE command is running
[tester::#CC3] client: Received bytes: "+string\r\n"
[tester::#CC3] client: Received RESP simple string: "string"
[tester::#CC3] Received "string"
[tester::#CC3] client: > TYPE missing_key_pineapple
[tester::#CC3] client: Sent bytes: "*2\r\n$4\r\nTYPE\r\n$21\r\nmissing_key_pineapple\r\n"
[your_program] multiBulk has 2 elements
[your_program] TYPE command is running
[tester::#CC3] client: Received bytes: "+none\r\n"
[tester::#CC3] client: Received RESP simple string: "none"
[tester::#CC3] Received "none"
[tester::#CC3] Test passed.
[tester::#CC3] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#CC3] Program terminated successfully
[tester::#SM4] Running tests for Stage #SM4 (RDB Persistence - Read value with expiry)
[tester::#SM4] Created RDB file with 4 key-value pairs: {"strawberry": "blueberry", "mango": "orange", "apple": "pear", "raspberry": "apple"}
[tester::#SM4] Hexdump of RDB file contents:
[tester::#SM4] Idx  | Hex                                             | ASCII
[tester::#SM4] -----+-------------------------------------------------+-----------------
[tester::#SM4] 0000 | 52 45 44 49 53 30 30 31 31 fa 09 72 65 64 69 73 | REDIS0011..redis
[tester::#SM4] 0010 | 2d 76 65 72 05 37 2e 32 2e 30 fa 0a 72 65 64 69 | -ver.7.2.0..redi
[tester::#SM4] 0020 | 73 2d 62 69 74 73 c0 40 fe 00 fb 04 04 fc 00 9c | s-bits.@........
[tester::#SM4] 0030 | ef 12 7e 01 00 00 00 0a 73 74 72 61 77 62 65 72 | ..~.....strawber
[tester::#SM4] 0040 | 72 79 09 62 6c 75 65 62 65 72 72 79 fc 00 0c 28 | ry.blueberry...(
[tester::#SM4] 0050 | 8a c7 01 00 00 00 05 6d 61 6e 67 6f 06 6f 72 61 | .......mango.ora
[tester::#SM4] 0060 | 6e 67 65 fc 00 0c 28 8a c7 01 00 00 00 05 61 70 | nge...(.......ap
[tester::#SM4] 0070 | 70 6c 65 04 70 65 61 72 fc 00 0c 28 8a c7 01 00 | ple.pear...(....
[tester::#SM4] 0080 | 00 00 09 72 61 73 70 62 65 72 72 79 05 61 70 70 | ...raspberry.app
[tester::#SM4] 0090 | 6c 65 ff 9e 21 5d 44 96 4a 2d e6 0a             | le..!]D.J-..
[tester::#SM4]
[tester::#SM4] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles342060659 --dbfilename apple.rdb
[your_program] put dir config/tmp/rdbfiles342060659
[your_program] put dir configapple.rdb
[your_program] rdb ->
[your_program] [82, 69, 68, 73, 83, 48, 48, 49, 49, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 4, 4, -4, 0, -100, -17, 18, 126, 1, 0, 0, 0, 10, 115, 116, 114, 97, 119, 98, 101, 114, 114, 121, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 5, 109, 97, 110, 103, 111, 6, 111, 114, 97, 110, 103, 101, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 5, 97, 112, 112, 108, 101, 4, 112, 101, 97, 114, -4, 0, 12, 40, -118, -57, 1, 0, 0, 0, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 5, 97, 112, 112, 108, 101, -1, -98, 33, 93, 68, -106, 74, 45, -26, 10]
[your_program] add expire data-> strawberry:blueberry
[your_program] add expire data-> mango:orange
[your_program] add expire data-> apple:pear
[your_program] add expire data-> raspberry:apple
[your_program] rdb: Rdb[auxFields=[AuxField[key=redis-ver, value=7.2.0], AuxField[key=redis-bits, value=64]], rdbDbInfos=[RdbDbInfo[dbNumber=0, hashTableSize=4, expireHashTableSize=4, rdbExpirePairs=[RdbExpirePair[expireTime=1640995200000, key=strawberry, value=blueberry], RdbExpirePair[expireTime=1956528000000, key=mango, value=orange], RdbExpirePair[expireTime=1956528000000, key=apple, value=pear], RdbExpirePair[expireTime=1956528000000, key=raspberry, value=apple]], rdbPairs=[]]]]
[your_program] put expire data, expire:1640995200000 key: strawberry
[your_program] put expire data, expire:1956528000000 key: mango
[your_program] put expire data, expire:1956528000000 key: apple
[your_program] put expire data, expire:1956528000000 key: raspberry
[tester::#SM4] client: $ redis-cli GET strawberry
[tester::#SM4] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$10\r\nstrawberry\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60776
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:strawberry
[your_program] GET -> now is 1731289790333
[your_program] GET -> target is 1640995200000
[your_program] get command exec failed,key:strawberry is expired
[tester::#SM4] client: Received bytes: "$-1\r\n"
[tester::#SM4] client: Received RESP null bulk string: "$-1\r\n"
[tester::#SM4] Received "$-1\r\n"
[tester::#SM4] client: > GET mango
[tester::#SM4] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$5\r\nmango\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:mango
[your_program] GET -> now is 1731289790341
[your_program] GET -> target is 1956528000000
[your_program] value is orange
[your_program] build response $6
[your_program] orange
[your_program]
[tester::#SM4] client: Received bytes: "$6\r\norange\r\n"
[tester::#SM4] client: Received RESP bulk string: "orange"
[tester::#SM4] Received "orange"
[tester::#SM4] client: > GET apple
[tester::#SM4] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$5\r\napple\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:apple
[your_program] GET -> now is 1731289790354
[your_program] GET -> target is 1956528000000
[your_program] value is pear
[your_program] build response $4
[your_program] pear
[your_program]
[tester::#SM4] client: Received bytes: "$4\r\npear\r\n"
[tester::#SM4] client: Received RESP bulk string: "pear"
[tester::#SM4] Received "pear"
[tester::#SM4] client: > GET raspberry
[tester::#SM4] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$9\r\nraspberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:raspberry
[your_program] GET -> now is 1731289790366
[your_program] GET -> target is 1956528000000
[your_program] value is apple
[your_program] build response $5
[your_program] apple
[your_program]
[tester::#SM4] client: Received bytes: "$5\r\napple\r\n"
[tester::#SM4] client: Received RESP bulk string: "apple"
[tester::#SM4] Received "apple"
[tester::#SM4] Test passed.
[tester::#SM4] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#SM4] Program terminated successfully
[tester::#DQ3] Running tests for Stage #DQ3 (RDB Persistence - Read multiple string values)
[tester::#DQ3] Created RDB file with 5 key-value pairs: {"grape": "blueberry", "pear": "banana", "strawberry": "strawberry", "mango": "mango", "raspberry": "orange"}
[tester::#DQ3] Hexdump of RDB file contents:
[tester::#DQ3] Idx  | Hex                                             | ASCII
[tester::#DQ3] -----+-------------------------------------------------+-----------------
[tester::#DQ3] 0000 | 52 45 44 49 53 30 30 31 31 fa 09 72 65 64 69 73 | REDIS0011..redis
[tester::#DQ3] 0010 | 2d 76 65 72 05 37 2e 32 2e 30 fa 0a 72 65 64 69 | -ver.7.2.0..redi
[tester::#DQ3] 0020 | 73 2d 62 69 74 73 c0 40 fe 00 fb 05 00 00 05 67 | s-bits.@.......g
[tester::#DQ3] 0030 | 72 61 70 65 09 62 6c 75 65 62 65 72 72 79 00 04 | rape.blueberry..
[tester::#DQ3] 0040 | 70 65 61 72 06 62 61 6e 61 6e 61 00 0a 73 74 72 | pear.banana..str
[tester::#DQ3] 0050 | 61 77 62 65 72 72 79 0a 73 74 72 61 77 62 65 72 | awberry.strawber
[tester::#DQ3] 0060 | 72 79 00 05 6d 61 6e 67 6f 05 6d 61 6e 67 6f 00 | ry..mango.mango.
[tester::#DQ3] 0070 | 09 72 61 73 70 62 65 72 72 79 06 6f 72 61 6e 67 | .raspberry.orang
[tester::#DQ3] 0080 | 65 ff f6 39 e7 40 57 33 66 63 0a                | e..9.@W3fc.
[tester::#DQ3]
[tester::#DQ3] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles351469340 --dbfilename blueberry.rdb
[your_program] put dir config/tmp/rdbfiles351469340
[your_program] put dir configblueberry.rdb
[your_program] rdb ->
[your_program] [82, 69, 68, 73, 83, 48, 48, 49, 49, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 5, 0, 0, 5, 103, 114, 97, 112, 101, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, 0, 4, 112, 101, 97, 114, 6, 98, 97, 110, 97, 110, 97, 0, 10, 115, 116, 114, 97, 119, 98, 101, 114, 114, 121, 10, 115, 116, 114, 97, 119, 98, 101, 114, 114, 121, 0, 5, 109, 97, 110, 103, 111, 5, 109, 97, 110, 103, 111, 0, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 6, 111, 114, 97, 110, 103, 101, -1, -10, 57, -25, 64, 87, 51, 102, 99, 10]
[your_program] add data-> grape:blueberry
[your_program] add data-> pear:banana
[your_program] add data-> strawberry:strawberry
[your_program] add data-> mango:mango
[your_program] add data-> raspberry:orange
[your_program] rdb: Rdb[auxFields=[AuxField[key=redis-ver, value=7.2.0], AuxField[key=redis-bits, value=64]], rdbDbInfos=[RdbDbInfo[dbNumber=0, hashTableSize=5, expireHashTableSize=0, rdbExpirePairs=[], rdbPairs=[RdbPair[key=grape, value=blueberry], RdbPair[key=pear, value=banana], RdbPair[key=strawberry, value=strawberry], RdbPair[key=mango, value=mango], RdbPair[key=raspberry, value=orange]]]]]
[tester::#DQ3] client: $ redis-cli GET grape
[tester::#DQ3] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$5\r\ngrape\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60796
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:grape
[your_program] value is blueberry
[your_program] build response $9
[your_program] blueberry
[your_program]
[tester::#DQ3] client: Received bytes: "$9\r\nblueberry\r\n"
[tester::#DQ3] client: Received RESP bulk string: "blueberry"
[tester::#DQ3] Received "blueberry"
[tester::#DQ3] client: > GET pear
[tester::#DQ3] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$4\r\npear\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:pear
[your_program] value is banana
[your_program] build response $6
[your_program] banana
[your_program]
[tester::#DQ3] client: Received bytes: "$6\r\nbanana\r\n"
[tester::#DQ3] client: Received RESP bulk string: "banana"
[tester::#DQ3] Received "banana"
[tester::#DQ3] client: > GET strawberry
[tester::#DQ3] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$10\r\nstrawberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:strawberry
[your_program] value is strawberry
[your_program] build response $10
[your_program] strawberry
[your_program]
[tester::#DQ3] client: Received bytes: "$10\r\nstrawberry\r\n"
[tester::#DQ3] client: Received RESP bulk string: "strawberry"
[tester::#DQ3] Received "strawberry"
[tester::#DQ3] client: > GET mango
[tester::#DQ3] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$5\r\nmango\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:mango
[your_program] value is mango
[your_program] build response $5
[your_program] mango
[your_program]
[tester::#DQ3] client: Received bytes: "$5\r\nmango\r\n"
[tester::#DQ3] client: Received RESP bulk string: "mango"
[tester::#DQ3] Received "mango"
[tester::#DQ3] client: > GET raspberry
[tester::#DQ3] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$9\r\nraspberry\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:raspberry
[your_program] value is orange
[your_program] build response $6
[your_program] orange
[your_program]
[tester::#DQ3] client: Received bytes: "$6\r\norange\r\n"
[tester::#DQ3] client: Received RESP bulk string: "orange"
[tester::#DQ3] Received "orange"
[tester::#DQ3] Test passed.
[tester::#DQ3] Terminating program
[tester::#DQ3] Program terminated successfully
[tester::#JW4] Running tests for Stage #JW4 (RDB Persistence - Read multiple keys)
[tester::#JW4] Created RDB file with 4 keys: ["grape", "blueberry", "banana", "raspberry"]
[tester::#JW4] Hexdump of RDB file contents:
[tester::#JW4] Idx  | Hex                                             | ASCII
[tester::#JW4] -----+-------------------------------------------------+-----------------
[tester::#JW4] 0000 | 52 45 44 49 53 30 30 31 31 fa 09 72 65 64 69 73 | REDIS0011..redis
[tester::#JW4] 0010 | 2d 76 65 72 05 37 2e 32 2e 30 fa 0a 72 65 64 69 | -ver.7.2.0..redi
[tester::#JW4] 0020 | 73 2d 62 69 74 73 c0 40 fe 00 fb 04 00 00 05 67 | s-bits.@.......g
[tester::#JW4] 0030 | 72 61 70 65 05 67 72 61 70 65 00 09 62 6c 75 65 | rape.grape..blue
[tester::#JW4] 0040 | 62 65 72 72 79 05 6d 61 6e 67 6f 00 06 62 61 6e | berry.mango..ban
[tester::#JW4] 0050 | 61 6e 61 09 72 61 73 70 62 65 72 72 79 00 09 72 | ana.raspberry..r
[tester::#JW4] 0060 | 61 73 70 62 65 72 72 79 06 6f 72 61 6e 67 65 ff | aspberry.orange.
[tester::#JW4] 0070 | 02 13 93 63 73 b6 ae 55 0a                      | ...cs..U.
[tester::#JW4]
[tester::#JW4] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles1433500321 --dbfilename apple.rdb
[your_program] put dir config/tmp/rdbfiles1433500321
[your_program] put dir configapple.rdb
[your_program] rdb ->
[your_program] [82, 69, 68, 73, 83, 48, 48, 49, 49, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 4, 0, 0, 5, 103, 114, 97, 112, 101, 5, 103, 114, 97, 112, 101, 0, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, 5, 109, 97, 110, 103, 111, 0, 6, 98, 97, 110, 97, 110, 97, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 0, 9, 114, 97, 115, 112, 98, 101, 114, 114, 121, 6, 111, 114, 97, 110, 103, 101, -1, 2, 19, -109, 99, 115, -74, -82, 85, 10]
[your_program] add data-> grape:grape
[your_program] add data-> blueberry:mango
[your_program] add data-> banana:raspberry
[your_program] add data-> raspberry:orange
[your_program] rdb: Rdb[auxFields=[AuxField[key=redis-ver, value=7.2.0], AuxField[key=redis-bits, value=64]], rdbDbInfos=[RdbDbInfo[dbNumber=0, hashTableSize=4, expireHashTableSize=0, rdbExpirePairs=[], rdbPairs=[RdbPair[key=grape, value=grape], RdbPair[key=blueberry, value=mango], RdbPair[key=banana, value=raspberry], RdbPair[key=raspberry, value=orange]]]]]
[tester::#JW4] client: $ redis-cli KEYS *
[tester::#JW4] client: Sent bytes: "*2\r\n$4\r\nKEYS\r\n$1\r\n*\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:60828
[your_program] multiBulk has 2 elements
[your_program] KEYS command is running
[your_program] build command ->*4
[your_program] $6
[your_program] banana
[your_program] $9
[your_program] raspberry
[your_program] $9
[your_program] blueberry
[your_program] $5
[your_program] grape
[your_program]
[tester::#JW4] client: Received bytes: "*4\r\n$6\r\nbanana\r\n$9\r\nraspberry\r\n$9\r\nblueberry\r\n$5\r\ngrape\r\n"
[tester::#JW4] client: Received RESP array: ["banana", "raspberry", "blueberry", "grape"]
[tester::#JW4] Received ["banana", "raspberry", "blueberry", "grape"]
[tester::#JW4] Test passed.
[tester::#JW4] Terminating program
[tester::#JW4] Program terminated successfully
[tester::#GC6] Running tests for Stage #GC6 (RDB Persistence - Read a string value)
[tester::#GC6] Created RDB file with a single key-value pair: {"apple": "banana"}
[tester::#GC6] Hexdump of RDB file contents:
[tester::#GC6] Idx  | Hex                                             | ASCII
[tester::#GC6] -----+-------------------------------------------------+-----------------
[tester::#GC6] 0000 | 52 45 44 49 53 30 30 31 31 fa 09 72 65 64 69 73 | REDIS0011..redis
[tester::#GC6] 0010 | 2d 76 65 72 05 37 2e 32 2e 30 fa 0a 72 65 64 69 | -ver.7.2.0..redi
[tester::#GC6] 0020 | 73 2d 62 69 74 73 c0 40 fe 00 fb 01 00 00 05 61 | s-bits.@.......a
[tester::#GC6] 0030 | 70 70 6c 65 06 62 61 6e 61 6e 61 ff 3f d7 e8 25 | pple.banana.?..%
[tester::#GC6] 0040 | b6 f2 cc 03 0a                                  | .....
[tester::#GC6]
[tester::#GC6] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles64133125 --dbfilename raspberry.rdb
[your_program] put dir config/tmp/rdbfiles64133125
[your_program] put dir configraspberry.rdb
[your_program] rdb ->
[your_program] [82, 69, 68, 73, 83, 48, 48, 49, 49, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -2, 0, -5, 1, 0, 0, 5, 97, 112, 112, 108, 101, 6, 98, 97, 110, 97, 110, 97, -1, 63, -41, -24, 37, -74, -14, -52, 3, 10]
[your_program] add data-> apple:banana
[your_program] rdb: Rdb[auxFields=[AuxField[key=redis-ver, value=7.2.0], AuxField[key=redis-bits, value=64]], rdbDbInfos=[RdbDbInfo[dbNumber=0, hashTableSize=1, expireHashTableSize=0, rdbExpirePairs=[], rdbPairs=[RdbPair[key=apple, value=banana]]]]]
[tester::#GC6] client: $ redis-cli GET apple
[tester::#GC6] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$5\r\napple\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49276
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:apple
[your_program] value is banana
[your_program] build response $6
[your_program] banana
[your_program]
[tester::#GC6] client: Received bytes: "$6\r\nbanana\r\n"
[tester::#GC6] client: Received RESP bulk string: "banana"
[tester::#GC6] Received "banana"
[tester::#GC6] Test passed.
[tester::#GC6] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[tester::#GC6] Program terminated successfully
[tester::#JZ6] Running tests for Stage #JZ6 (RDB Persistence - Read a key)
[tester::#JZ6] Created RDB file with a single key: ["blueberry"]
[tester::#JZ6] Hexdump of RDB file contents:
[tester::#JZ6] Idx  | Hex                                             | ASCII
[tester::#JZ6] -----+-------------------------------------------------+-----------------
[tester::#JZ6] 0000 | 52 45 44 49 53 30 30 31 31 fa 0a 72 65 64 69 73 | REDIS0011..redis
[tester::#JZ6] 0010 | 2d 62 69 74 73 c0 40 fa 09 72 65 64 69 73 2d 76 | -bits.@..redis-v
[tester::#JZ6] 0020 | 65 72 05 37 2e 32 2e 30 fe 00 fb 01 00 00 09 62 | er.7.2.0.......b
[tester::#JZ6] 0030 | 6c 75 65 62 65 72 72 79 05 61 70 70 6c 65 ff fe | lueberry.apple..
[tester::#JZ6] 0040 | ed e0 83 a3 4f 85 7d 0a                         | ....O.}.
[tester::#JZ6]
[tester::#JZ6] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles3791535034 --dbfilename blueberry.rdb
[your_program] put dir config/tmp/rdbfiles3791535034
[your_program] put dir configblueberry.rdb
[your_program] rdb ->
[your_program] [82, 69, 68, 73, 83, 48, 48, 49, 49, -6, 10, 114, 101, 100, 105, 115, 45, 98, 105, 116, 115, -64, 64, -6, 9, 114, 101, 100, 105, 115, 45, 118, 101, 114, 5, 55, 46, 50, 46, 48, -2, 0, -5, 1, 0, 0, 9, 98, 108, 117, 101, 98, 101, 114, 114, 121, 5, 97, 112, 112, 108, 101, -1, -2, -19, -32, -125, -93, 79, -123, 125, 10]
[your_program] add data-> blueberry:apple
[your_program] rdb: Rdb[auxFields=[AuxField[key=redis-bits, value=64], AuxField[key=redis-ver, value=7.2.0]], rdbDbInfos=[RdbDbInfo[dbNumber=0, hashTableSize=1, expireHashTableSize=0, rdbExpirePairs=[], rdbPairs=[RdbPair[key=blueberry, value=apple]]]]]
[tester::#JZ6] client: $ redis-cli KEYS *
[tester::#JZ6] client: Sent bytes: "*2\r\n$4\r\nKEYS\r\n$1\r\n*\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49320
[your_program] multiBulk has 2 elements
[your_program] KEYS command is running
[your_program] build command ->*1
[your_program] $9
[your_program] blueberry
[your_program]
[tester::#JZ6] client: Received bytes: "*1\r\n$9\r\nblueberry\r\n"
[tester::#JZ6] client: Received RESP array: ["blueberry"]
[tester::#JZ6] Received ["blueberry"]
[tester::#JZ6] Test passed.
[tester::#JZ6] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#JZ6] Program terminated successfully
[tester::#ZG5] Running tests for Stage #ZG5 (RDB Persistence - RDB file config)
[tester::#ZG5] $ ./spawn_redis_server.sh --dir /tmp/rdbfiles1262050416 --dbfilename pear.rdb
[your_program] put dir config/tmp/rdbfiles1262050416
[your_program] put dir configpear.rdb
[your_program] redis failed - rdb file is notfound
[tester::#ZG5] client: $ redis-cli CONFIG GET dir
[tester::#ZG5] client: Sent bytes: "*3\r\n$6\r\nCONFIG\r\n$3\r\nGET\r\n$3\r\ndir\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49348
[your_program] multiBulk has 3 elements
[your_program] CONFIG command is running
[your_program] CONFIG -> GET:dir
[your_program] CONFIG value is /tmp/rdbfiles1262050416
[your_program] build command ->*2
[your_program] $3
[your_program] dir
[your_program] $23
[your_program] /tmp/rdbfiles1262050416
[your_program]
[tester::#ZG5] client: Received bytes: "*2\r\n$3\r\ndir\r\n$23\r\n/tmp/rdbfiles1262050416\r\n"
[tester::#ZG5] client: Received RESP array: ["dir", "/tmp/rdbfiles1262050416"]
[tester::#ZG5] Received ["dir", "/tmp/rdbfiles1262050416"]
[tester::#ZG5] Test passed.
[tester::#ZG5] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#ZG5] Program terminated successfully
[tester::#NA2] Running tests for Stage #NA2 (Replication - WAIT with multiple commands)
[tester::#NA2] $ ./spawn_redis_server.sh --port 6379
[tester::#NA2] Proceeding to create 3 replicas.
[tester::#NA2] Creating replica: 1
[your_program] not found rdb properties
[tester::#NA2] [handshake] replica-1: $ redis-cli PING
[tester::#NA2] [handshake] replica-1: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49360
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#NA2] [handshake] replica-1: Received bytes: "$4\r\nPONG\r\n"
[tester::#NA2] [handshake] replica-1: Received RESP bulk string: "PONG"
[tester::#NA2] [handshake] Received "PONG"
[tester::#NA2] [handshake] replica-1: > REPLCONF listening-port 6380
[tester::#NA2] [handshake] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6380\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] replica-1: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-1: Received RESP simple string: "OK"
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-1: > REPLCONF capa psync2
[tester::#NA2] [handshake] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] replica-1: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-1: Received RESP simple string: "OK"
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-1: > PSYNC ? -1
[tester::#NA2] [handshake] replica-1: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[your_program] PSYNC command is running
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49360
[tester::#NA2] [handshake] replica-1: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#NA2] [handshake] replica-1: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Reading RDB file...
[tester::#NA2] [handshake] replica-1: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#NA2] [handshake] Received RDB file
[tester::#NA2] Creating replica: 2
[tester::#NA2] [handshake] replica-2: $ redis-cli PING
[tester::#NA2] [handshake] replica-2: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49376
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#NA2] [handshake] replica-2: Received bytes: "$4\r\nPONG\r\n"
[tester::#NA2] [handshake] replica-2: Received RESP bulk string: "PONG"
[tester::#NA2] [handshake] Received "PONG"
[tester::#NA2] [handshake] replica-2: > REPLCONF listening-port 6381
[tester::#NA2] [handshake] replica-2: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6381\r\n"
[tester::#NA2] [handshake] replica-2: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-2: Received RESP simple string: "OK"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-2: > REPLCONF capa psync2
[tester::#NA2] [handshake] replica-2: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] replica-2: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-2: Received RESP simple string: "OK"
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-2: > PSYNC ? -1
[tester::#NA2] [handshake] replica-2: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[tester::#NA2] [handshake] replica-2: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#NA2] [handshake] replica-2: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Reading RDB file...
[your_program] PSYNC command is running
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49376
[tester::#NA2] [handshake] replica-2: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#NA2] [handshake] Received RDB file
[tester::#NA2] Creating replica: 3
[tester::#NA2] [handshake] replica-3: $ redis-cli PING
[tester::#NA2] [handshake] replica-3: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49392
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#NA2] [handshake] replica-3: Received bytes: "$4\r\nPONG\r\n"
[tester::#NA2] [handshake] replica-3: Received RESP bulk string: "PONG"
[tester::#NA2] [handshake] Received "PONG"
[tester::#NA2] [handshake] replica-3: > REPLCONF listening-port 6382
[tester::#NA2] [handshake] replica-3: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6382\r\n"
[your_program] multiBulk has 3 elements
[tester::#NA2] [handshake] replica-3: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-3: Received RESP simple string: "OK"
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-3: > REPLCONF capa psync2
[tester::#NA2] [handshake] replica-3: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[tester::#NA2] [handshake] replica-3: Received bytes: "+OK\r\n"
[tester::#NA2] [handshake] replica-3: Received RESP simple string: "OK"
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#NA2] [handshake] Received "OK"
[tester::#NA2] [handshake] replica-3: > PSYNC ? -1
[tester::#NA2] [handshake] replica-3: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[your_program] PSYNC command is running
[tester::#NA2] [handshake] replica-3: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#NA2] [handshake] replica-3: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#NA2] [handshake] Reading RDB file...
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49392
[tester::#NA2] [handshake] replica-3: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#NA2] [handshake] Received RDB file
[tester::#NA2] [test] client: $ redis-cli SET foo 123
[tester::#NA2] [test] client: Sent bytes: "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\n123\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49396
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:foo 123
[your_program] start send SETbuild command ->*3
[your_program] $3
[your_program] SET
[your_program] $3
[your_program] foo
[your_program] $3
[your_program] 123
[your_program]
[your_program] sending 0 command to slave
[your_program] sending 1 command to slave
[your_program] sending 2 command to slave
[your_program] SET offset add: 31
[tester::#NA2] [test] client: Received bytes: "+OK\r\n"
[tester::#NA2] [test] client: Received RESP simple string: "OK"
[tester::#NA2] [test] Received "OK"
[tester::#NA2] [test] client: > WAIT 1 500
[tester::#NA2] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n1\r\n$3\r\n500\r\n"
[tester::#NA2] [test] Testing Replica : 1
[tester::#NA2] [test] replica-1: Expecting "SET foo 123" to be propagated
[tester::#NA2] [test] replica-1: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\n123\r\n"
[tester::#NA2] [test] replica-1: Received RESP array: ["SET", "foo", "123"]
[tester::#NA2] [test] Received ["SET", "foo", "123"]
[tester::#NA2] [test] replica-1: Expecting "REPLCONF GETACK *" from Master
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[tester::#NA2] [test] replica-1: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-1: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-1: Sending ACK to Master
[tester::#NA2] [test] replica-1: > REPLCONF ACK 31
[tester::#NA2] [test] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$2\r\n31\r\n"
[tester::#NA2] [test] Testing Replica : 2
[tester::#NA2] [test] replica-2: Expecting "SET foo 123" to be propagated
[tester::#NA2] [test] replica-2: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\n123\r\n"
[tester::#NA2] [test] replica-2: Received RESP array: ["SET", "foo", "123"]
[tester::#NA2] [test] Received ["SET", "foo", "123"]
[tester::#NA2] [test] replica-2: Expecting "REPLCONF GETACK *" from Master
[your_program] start send WAITmultiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] ReplConf -> recevied ACK
[your_program] response is null
[tester::#NA2] [test] replica-2: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-2: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-2: Not sending ACK to Master
[tester::#NA2] [test] Testing Replica : 3
[tester::#NA2] [test] replica-3: Expecting "SET foo 123" to be propagated
[tester::#NA2] [test] replica-3: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\n123\r\n"
[tester::#NA2] [test] replica-3: Received RESP array: ["SET", "foo", "123"]
[tester::#NA2] [test] Received ["SET", "foo", "123"]
[tester::#NA2] [test] replica-3: Expecting "REPLCONF GETACK *" from Master
[tester::#NA2] [test] replica-3: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-3: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-3: Not sending ACK to Master
[tester::#NA2] [test] client: Received bytes: ":1\r\n"
[tester::#NA2] [test] client: Received RESP integer: 1
[tester::#NA2] [test] Passed first WAIT test.
[tester::#NA2] [test] client: > SET baz 789
[tester::#NA2] [test] client: Sent bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbaz\r\n$3\r\n789\r\n"
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:baz 789
[your_program] start send SETbuild command ->*3
[your_program] $3
[your_program] SET
[your_program] $3
[your_program] baz
[your_program] $3
[your_program] 789
[your_program]
[your_program] sending 0 command to slave
[your_program] sending 1 command to slave
[your_program] sending 2 command to slave
[your_program] SET offset add: 31
[tester::#NA2] [test] client: Received bytes: "+OK\r\n"
[tester::#NA2] [test] client: Received RESP simple string: "OK"
[tester::#NA2] [test] Received "OK"
[tester::#NA2] [test] client: > WAIT 3 2000
[tester::#NA2] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n3\r\n$4\r\n2000\r\n"
[tester::#NA2] [test] Testing Replica : 1
[tester::#NA2] [test] replica-1: Expecting "SET baz 789" to be propagated
[tester::#NA2] [test] replica-1: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbaz\r\n$3\r\n789\r\n"
[tester::#NA2] [test] replica-1: Received RESP array: ["SET", "baz", "789"]
[tester::#NA2] [test] Received ["SET", "baz", "789"]
[tester::#NA2] [test] replica-1: Expecting "REPLCONF GETACK *" from Master
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[tester::#NA2] [test] replica-1: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-1: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-1: Sending ACK to Master
[tester::#NA2] [test] replica-1: > REPLCONF ACK 99
[tester::#NA2] [test] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$2\r\n99\r\n"
[tester::#NA2] [test] Testing Replica : 2
[tester::#NA2] [test] replica-2: Expecting "SET baz 789" to be propagated
[tester::#NA2] [test] replica-2: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbaz\r\n$3\r\n789\r\n"
[tester::#NA2] [test] replica-2: Received RESP array: ["SET", "baz", "789"]
[tester::#NA2] [test] Received ["SET", "baz", "789"]
[tester::#NA2] [test] replica-2: Expecting "REPLCONF GETACK *" from Master
[your_program] start send WAITmultiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] ReplConf -> recevied ACK
[your_program] response is null
[tester::#NA2] [test] replica-2: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-2: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-2: Sending ACK to Master
[tester::#NA2] [test] replica-2: > REPLCONF ACK 99
[tester::#NA2] [test] replica-2: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$2\r\n99\r\n"
[tester::#NA2] [test] Testing Replica : 3
[tester::#NA2] [test] replica-3: Expecting "SET baz 789" to be propagated
[tester::#NA2] [test] replica-3: Received bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbaz\r\n$3\r\n789\r\n"
[tester::#NA2] [test] replica-3: Received RESP array: ["SET", "baz", "789"]
[tester::#NA2] [test] Received ["SET", "baz", "789"]
[tester::#NA2] [test] replica-3: Expecting "REPLCONF GETACK *" from Master
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] ReplConf -> recevied ACK
[your_program] response is null
[tester::#NA2] [test] replica-3: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[tester::#NA2] [test] replica-3: Received RESP array: ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] Received ["REPLCONF", "GETACK", "*"]
[tester::#NA2] [test] replica-3: Not sending ACK to Master
[your_program] timeout, return current count:2
[tester::#NA2] [test] client: Received bytes: ":2\r\n"
[tester::#NA2] [test] client: Received RESP integer: 2
[tester::#NA2] [test] WAIT command returned after 2007 ms
[your_program] Exception in thread "Thread-1" java.lang.IllegalArgumentException: data is null
[tester::#NA2] Test passed.
[tester::#NA2] Terminating program
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-2" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-3" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#NA2] Program terminated successfully
[tester::#TU8] Running tests for Stage #TU8 (Replication - WAIT with no commands)
[tester::#TU8] $ ./spawn_redis_server.sh --port 6379
[tester::#TU8] Proceeding to create 4 replicas.
[tester::#TU8] Creating replica: 1
[your_program] not found rdb properties
[tester::#TU8] [handshake] replica-1: $ redis-cli PING
[tester::#TU8] [handshake] replica-1: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49414
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#TU8] [handshake] replica-1: Received bytes: "$4\r\nPONG\r\n"
[tester::#TU8] [handshake] replica-1: Received RESP bulk string: "PONG"
[tester::#TU8] [handshake] Received "PONG"
[tester::#TU8] [handshake] replica-1: > REPLCONF listening-port 6380
[tester::#TU8] [handshake] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6380\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-1: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-1: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-1: > REPLCONF capa psync2
[tester::#TU8] [handshake] replica-1: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-1: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-1: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-1: > PSYNC ? -1
[tester::#TU8] [handshake] replica-1: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[your_program] PSYNC command is running
[tester::#TU8] [handshake] replica-1: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#TU8] [handshake] replica-1: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Reading RDB file...
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49414
[tester::#TU8] [handshake] replica-1: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#TU8] [handshake] Received RDB file
[tester::#TU8] Creating replica: 2
[tester::#TU8] [handshake] replica-2: $ redis-cli PING
[tester::#TU8] [handshake] replica-2: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49428
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#TU8] [handshake] replica-2: Received bytes: "$4\r\nPONG\r\n"
[tester::#TU8] [handshake] replica-2: Received RESP bulk string: "PONG"
[tester::#TU8] [handshake] Received "PONG"
[tester::#TU8] [handshake] replica-2: > REPLCONF listening-port 6381
[tester::#TU8] [handshake] replica-2: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6381\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-2: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-2: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-2: > REPLCONF capa psync2
[tester::#TU8] [handshake] replica-2: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-2: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-2: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-2: > PSYNC ? -1
[tester::#TU8] [handshake] replica-2: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[tester::#TU8] [handshake] replica-2: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#TU8] [handshake] replica-2: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Reading RDB file...
[your_program] PSYNC command is running
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49428
[tester::#TU8] [handshake] replica-2: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#TU8] [handshake] Received RDB file
[tester::#TU8] Creating replica: 3
[tester::#TU8] [handshake] replica-3: $ redis-cli PING
[tester::#TU8] [handshake] replica-3: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49434
[tester::#TU8] [handshake] replica-3: Received bytes: "$4\r\nPONG\r\n"
[tester::#TU8] [handshake] replica-3: Received RESP bulk string: "PONG"
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#TU8] [handshake] Received "PONG"
[tester::#TU8] [handshake] replica-3: > REPLCONF listening-port 6382
[tester::#TU8] [handshake] replica-3: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6382\r\n"
[your_program] multiBulk has 3 elements
[tester::#TU8] [handshake] replica-3: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-3: Received RESP simple string: "OK"
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-3: > REPLCONF capa psync2
[tester::#TU8] [handshake] replica-3: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[tester::#TU8] [handshake] replica-3: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-3: Received RESP simple string: "OK"
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-3: > PSYNC ? -1
[tester::#TU8] [handshake] replica-3: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[tester::#TU8] [handshake] replica-3: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#TU8] [handshake] replica-3: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Reading RDB file...
[your_program] PSYNC command is running
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49434
[tester::#TU8] [handshake] replica-3: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#TU8] [handshake] Received RDB file
[tester::#TU8] Creating replica: 4
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49448
[tester::#TU8] [handshake] replica-4: $ redis-cli PING
[tester::#TU8] [handshake] replica-4: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[tester::#TU8] [handshake] replica-4: Received bytes: "$4\r\nPONG\r\n"
[tester::#TU8] [handshake] replica-4: Received RESP bulk string: "PONG"
[tester::#TU8] [handshake] Received "PONG"
[tester::#TU8] [handshake] replica-4: > REPLCONF listening-port 6383
[tester::#TU8] [handshake] replica-4: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6383\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-4: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-4: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-4: > REPLCONF capa psync2
[tester::#TU8] [handshake] replica-4: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[tester::#TU8] [handshake] replica-4: Received bytes: "+OK\r\n"
[tester::#TU8] [handshake] replica-4: Received RESP simple string: "OK"
[tester::#TU8] [handshake] Received "OK"
[tester::#TU8] [handshake] replica-4: > PSYNC ? -1
[tester::#TU8] [handshake] replica-4: Sent bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[your_program] multiBulk has 3 elements
[your_program] PSYNC command is running
[tester::#TU8] [handshake] replica-4: Received bytes: "+FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0\r\n"
[tester::#TU8] [handshake] replica-4: Received RESP simple string: "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Received "FULLRESYNC 8371b4fb1155b71f4a04d3e1bc3e18c4a990aeeb 0"
[tester::#TU8] [handshake] Reading RDB file...
[your_program] send empty RDB
[your_program] connect slave->0:0:0:0:0:0:0:1:49448
[tester::#TU8] [handshake] replica-4: Received bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#TU8] [handshake] Received RDB file
[tester::#TU8] [test] client: $ redis-cli WAIT 3 500
[tester::#TU8] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n3\r\n$3\r\n500\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49450
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[your_program] start send WAITnot presence send commands
[your_program] slave size : 4
[tester::#TU8] [test] client: Received bytes: ":4\r\n"
[tester::#TU8] [test] client: Received RESP integer: 4
[tester::#TU8] [test] Received 4
[tester::#TU8] [test] client: > WAIT 4 500
[tester::#TU8] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n4\r\n$3\r\n500\r\n"
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[your_program] start send WAITnot presence send commands
[your_program] slave size : 4
[tester::#TU8] [test] client: Received bytes: ":4\r\n"
[tester::#TU8] [test] client: Received RESP integer: 4
[tester::#TU8] [test] Received 4
[tester::#TU8] [test] client: > WAIT 5 500
[tester::#TU8] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n5\r\n$3\r\n500\r\n"
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[your_program] start send WAITnot presence send commands
[your_program] slave size : 4
[tester::#TU8] [test] client: Received bytes: ":4\r\n"
[tester::#TU8] [test] client: Received RESP integer: 4
[tester::#TU8] [test] Received 4
[tester::#TU8] [test] client: > WAIT 6 500
[tester::#TU8] [test] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n6\r\n$3\r\n500\r\n"
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[your_program] start send WAITnot presence send commands
[your_program] slave size : 4
[tester::#TU8] [test] client: Received bytes: ":4\r\n"
[tester::#TU8] [test] client: Received RESP integer: 4
[tester::#TU8] [test] Received 4
[tester::#TU8] Test passed.
[tester::#TU8] Terminating program
[your_program] Exception in thread "Thread-4" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-3" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:106)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-2" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:106)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-1" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:106)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:106)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#TU8] Program terminated successfully
[tester::#MY8] Running tests for Stage #MY8 (Replication - WAIT with no replicas)
[tester::#MY8] $ ./spawn_redis_server.sh --port 6379
[your_program] not found rdb properties
[tester::#MY8] client: $ redis-cli WAIT 0 60000
[tester::#MY8] client: Sent bytes: "*3\r\n$4\r\nWAIT\r\n$1\r\n0\r\n$5\r\n60000\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49470
[your_program] multiBulk has 3 elements
[your_program] WAIT command is running
[your_program] wait is running
[your_program] Wait -> send REPLCONF command to slave
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $6
[your_program] GETACK
[your_program] $1
[your_program] *
[your_program]
[your_program] not slave node
[your_program] not presence send commands
[your_program] slave size : 0
[tester::#MY8] client: Received bytes: ":0\r\n"
[tester::#MY8] client: Received RESP integer: 0
[tester::#MY8] Received 0
[tester::#MY8] Test passed.
[tester::#MY8] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#MY8] Program terminated successfully
[tester::#YD3] Running tests for Stage #YD3 (Replication - ACKs with commands)
[tester::#YD3] Master is running on port 6379
[tester::#YD3] $ ./spawn_redis_server.sh --port 6380 --replicaof "localhost 6379"
[tester::#YD3] [handshake] master: Waiting for replica to initiate handshake with "PING" command
[your_program] send ping to master
[tester::#YD3] [handshake] master: Received bytes: "*1\r\n$4\r\nping\r\n"
[tester::#YD3] [handshake] master: Received RESP array: ["ping"]
[tester::#YD3] [handshake] Received ["ping"]
[tester::#YD3] [handshake] master: Sent "PONG"
[tester::#YD3] [handshake] master: Sent bytes: "+PONG\r\n"
[tester::#YD3] [handshake] master: Waiting for replica to send "REPLCONF listening-port 6380" command
[your_program] +PONG
[tester::#YD3] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6380\r\n"
[tester::#YD3] [handshake] master: Received RESP array: ["REPLCONF", "listening-port", "6380"]
[tester::#YD3] [handshake] Received ["REPLCONF", "listening-port", "6380"]
[tester::#YD3] [handshake] master: Sent "OK"
[tester::#YD3] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#YD3] [handshake] master: Waiting for replica to send "REPLCONF capa" command
[your_program] send [REPLCONF, listening-port, 6380] to master
[your_program] +OK
[your_program] send [REPLCONF, capa, psync2] to master
[tester::#YD3] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[tester::#YD3] [handshake] master: Received RESP array: ["REPLCONF", "capa", "psync2"]
[tester::#YD3] [handshake] Received ["REPLCONF", "capa", "psync2"]
[tester::#YD3] [handshake] master: Sent "OK"
[tester::#YD3] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#YD3] [handshake] master: Waiting for replica to send "PSYNC" command
[your_program] +OK
[your_program] send [PSYNC, ?, -1] to master
[tester::#YD3] [handshake] master: Received bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[tester::#YD3] [handshake] master: Received RESP array: ["PSYNC", "?", "-1"]
[tester::#YD3] [handshake] Received ["PSYNC", "?", "-1"]
[tester::#YD3] [handshake] master: Sent "FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0"
[tester::#YD3] [handshake] master: Sent bytes: "+FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0\r\n"
[tester::#YD3] [handshake] Sending RDB file...
[tester::#YD3] [handshake] master: Sent bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#YD3] [handshake] Sent RDB file.
[tester::#YD3] [test] master: > REPLCONF GETACK *
[tester::#YD3] [test] master: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[your_program] +FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0
[your_program] $88
[your_program] REDIS0011�	redis-ver7.2.0�
[your_program] redis-bits�@�ctime�m�e�used-mem°��aof-base���n;���Z�
[your_program] not found rdb properties
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
[tester::#YD3] [test] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$1\r\n0\r\n"
[tester::#YD3] [test] master: Received RESP array: ["REPLCONF", "ACK", "0"]
[tester::#YD3] [test] Received ["REPLCONF", "ACK", "0"]
[tester::#YD3] [propagation] master: > PING
[tester::#YD3] [propagation] master: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[tester::#YD3] [test] master: > REPLCONF GETACK *
[tester::#YD3] [test] master: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[your_program] multiBulk has 1 elements
[your_program] PING command is running
[your_program] build response $4
[your_program] PONG
[your_program]
[your_program] build command ->*1
[your_program] $4
[your_program] PING
[your_program]
[your_program] PING offset add: 14
[your_program] The slave's ping command cancels the reply
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $3
[your_program] ACK
[your_program] $2
[your_program] 51
[your_program]
[tester::#YD3] [test] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$2\r\n51\r\n"
[tester::#YD3] [test] master: Received RESP array: ["REPLCONF", "ACK", "51"]
[tester::#YD3] [test] Received ["REPLCONF", "ACK", "51"]
[tester::#YD3] [propagation] master: > SET grape strawberry
[tester::#YD3] [propagation] master: Sent bytes: "*3\r\n$3\r\nSET\r\n$5\r\ngrape\r\n$10\r\nstrawberry\r\n"
[tester::#YD3] [propagation] master: > SET strawberry pear
[tester::#YD3] [propagation] master: Sent bytes: "*3\r\n$3\r\nSET\r\n$10\r\nstrawberry\r\n$4\r\npear\r\n"
[tester::#YD3] [test] master: > REPLCONF GETACK *
[tester::#YD3] [test] master: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:grape strawberry
[your_program] build command ->*3
[your_program] $3
[your_program] SET
[your_program] $5
[your_program] grape
[your_program] $10
[your_program] strawberry
[your_program]
[your_program] SET offset add: 92
[your_program] The slave's write command cancels the reply
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:strawberry pear
[your_program] build command ->*3
[your_program] $3
[your_program] SET
[your_program] $10
[your_program] strawberry
[your_program] $4
[your_program] pear
[your_program]
[your_program] SET offset add: 132
[your_program] The slave's write command cancels the reply
[your_program] multiBulk has 3 elements
[your_program] REPLCONF command is running
[your_program] REPLCONF is running
[your_program] build command ->*3
[your_program] $8
[your_program] REPLCONF
[your_program] $3
[your_program] ACK
[your_program] $3
[your_program] 169
[your_program]
[tester::#YD3] [test] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$3\r\n169\r\n"
[tester::#YD3] [test] master: Received RESP array: ["REPLCONF", "ACK", "169"]
[tester::#YD3] [test] Received ["REPLCONF", "ACK", "169"]
[tester::#YD3] Test passed.
[tester::#YD3] Terminating program
[tester::#YD3] Program terminated successfully
[tester::#XV6] Running tests for Stage #XV6 (Replication - ACKs with no commands)
[tester::#XV6] Master is running on port 6379
[tester::#XV6] $ ./spawn_redis_server.sh --port 6380 --replicaof "localhost 6379"
[tester::#XV6] [handshake] master: Waiting for replica to initiate handshake with "PING" command
[your_program] send ping to master
[tester::#XV6] [handshake] master: Received bytes: "*1\r\n$4\r\nping\r\n"
[tester::#XV6] [handshake] master: Received RESP array: ["ping"]
[tester::#XV6] [handshake] Received ["ping"]
[tester::#XV6] [handshake] master: Sent "PONG"
[tester::#XV6] [handshake] master: Sent bytes: "+PONG\r\n"
[tester::#XV6] [handshake] master: Waiting for replica to send "REPLCONF listening-port 6380" command
[your_program] +PONG
[your_program] send [REPLCONF, listening-port, 6380] to master
[tester::#XV6] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6380\r\n"
[tester::#XV6] [handshake] master: Received RESP array: ["REPLCONF", "listening-port", "6380"]
[tester::#XV6] [handshake] Received ["REPLCONF", "listening-port", "6380"]
[tester::#XV6] [handshake] master: Sent "OK"
[tester::#XV6] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#XV6] [handshake] master: Waiting for replica to send "REPLCONF capa" command
[your_program] +OK
[your_program] send [REPLCONF, capa, psync2] to master
[tester::#XV6] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[tester::#XV6] [handshake] master: Received RESP array: ["REPLCONF", "capa", "psync2"]
[tester::#XV6] [handshake] Received ["REPLCONF", "capa", "psync2"]
[tester::#XV6] [handshake] master: Sent "OK"
[tester::#XV6] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#XV6] [handshake] master: Waiting for replica to send "PSYNC" command
[your_program] +OK
[your_program] send [PSYNC, ?, -1] to master
[tester::#XV6] [handshake] master: Received bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[tester::#XV6] [handshake] master: Received RESP array: ["PSYNC", "?", "-1"]
[tester::#XV6] [handshake] Received ["PSYNC", "?", "-1"]
[tester::#XV6] [handshake] master: Sent "FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0"
[tester::#XV6] [handshake] master: Sent bytes: "+FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0\r\n"
[tester::#XV6] [handshake] Sending RDB file...
[tester::#XV6] [handshake] master: Sent bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#XV6] [handshake] Sent RDB file.
[tester::#XV6] [test] master: > REPLCONF GETACK *
[tester::#XV6] [test] master: Sent bytes: "*3\r\n$8\r\nREPLCONF\r\n$6\r\nGETACK\r\n$1\r\n*\r\n"
[your_program] +FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0
[your_program] $88
[your_program] REDIS0011�	redis-ver7.2.0�
[your_program] redis-bits�@�ctime�m�e�used-mem°��aof-base���n;���Z�
[your_program] not found rdb properties
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
[tester::#XV6] [test] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$3\r\nACK\r\n$1\r\n0\r\n"
[tester::#XV6] [test] master: Received RESP array: ["REPLCONF", "ACK", "0"]
[tester::#XV6] [test] Received ["REPLCONF", "ACK", "0"]
[tester::#XV6] Test passed.
[tester::#XV6] Terminating program
[tester::#XV6] Program terminated successfully
[tester::#YG4] Running tests for Stage #YG4 (Replication - Command Processing)
[tester::#YG4] Master is running on port 6379
[tester::#YG4] $ ./spawn_redis_server.sh --port 6380 --replicaof "localhost 6379"
[tester::#YG4] [handshake] master: Waiting for replica to initiate handshake with "PING" command
[your_program] send ping to master
[tester::#YG4] [handshake] master: Received bytes: "*1\r\n$4\r\nping\r\n"
[tester::#YG4] [handshake] master: Received RESP array: ["ping"]
[tester::#YG4] [handshake] Received ["ping"]
[tester::#YG4] [handshake] master: Sent "PONG"
[tester::#YG4] [handshake] master: Sent bytes: "+PONG\r\n"
[tester::#YG4] [handshake] master: Waiting for replica to send "REPLCONF listening-port 6380" command
[your_program] +PONG
[your_program] send [REPLCONF, listening-port, 6380] to master
[tester::#YG4] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$14\r\nlistening-port\r\n$4\r\n6380\r\n"
[tester::#YG4] [handshake] master: Received RESP array: ["REPLCONF", "listening-port", "6380"]
[tester::#YG4] [handshake] Received ["REPLCONF", "listening-port", "6380"]
[tester::#YG4] [handshake] master: Sent "OK"
[tester::#YG4] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#YG4] [handshake] master: Waiting for replica to send "REPLCONF capa" command
[your_program] +OK
[your_program] send [REPLCONF, capa, psync2] to master
[tester::#YG4] [handshake] master: Received bytes: "*3\r\n$8\r\nREPLCONF\r\n$4\r\ncapa\r\n$6\r\npsync2\r\n"
[tester::#YG4] [handshake] master: Received RESP array: ["REPLCONF", "capa", "psync2"]
[tester::#YG4] [handshake] Received ["REPLCONF", "capa", "psync2"]
[tester::#YG4] [handshake] master: Sent "OK"
[tester::#YG4] [handshake] master: Sent bytes: "+OK\r\n"
[tester::#YG4] [handshake] master: Waiting for replica to send "PSYNC" command
[your_program] +OK
[your_program] send [PSYNC, ?, -1] to master
[tester::#YG4] [handshake] master: Received bytes: "*3\r\n$5\r\nPSYNC\r\n$1\r\n?\r\n$2\r\n-1\r\n"
[tester::#YG4] [handshake] master: Received RESP array: ["PSYNC", "?", "-1"]
[tester::#YG4] [handshake] Received ["PSYNC", "?", "-1"]
[tester::#YG4] [handshake] master: Sent "FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0"
[tester::#YG4] [handshake] master: Sent bytes: "+FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0\r\n"
[tester::#YG4] [handshake] Sending RDB file...
[tester::#YG4] [handshake] master: Sent bytes: "$88\r\nREDIS0011\xfa\tredis-ver\x057.2.0\xfa\nredis-bits\xc0@\xfa\x05ctime\xc2m\b\xbce\xfa\bused-mem°\xc4\x10\x00\xfa\baof-base\xc0\x00\xff\xf0n;\xfe\xc0\xffZ\xa2"
[tester::#YG4] [handshake] Sent RDB file.
[your_program] +FULLRESYNC 75cd7bc10c49047e0d163660f3b90625b1af31dc 0
[your_program] $88
[your_program] REDIS0011�	redis-ver7.2.0�
[your_program] redis-bits�@�ctime�m�e�used-mem°��aof-base���n;���Z�
[your_program] not found rdb properties
[tester::#YG4] [propagation] master: > SET foo 123
[tester::#YG4] [propagation] master: Sent bytes: "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\n123\r\n"
[tester::#YG4] [propagation] master: > SET bar 456
[tester::#YG4] [propagation] master: Sent bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbar\r\n$3\r\n456\r\n"
[tester::#YG4] [propagation] master: > SET baz 789
[tester::#YG4] [propagation] master: Sent bytes: "*3\r\n$3\r\nSET\r\n$3\r\nbaz\r\n$3\r\n789\r\n"
[tester::#YG4] [test] Getting key foo
[tester::#YG4] [test] client: $ redis-cli GET foo
[tester::#YG4] [test] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r\n"
[your_program] multiBulk has 3 elements
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:52378
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:foo
[your_program] SET command is running
[your_program] value is null, return $-1
[your_program]
[your_program] set command param:foo 123
[your_program] The slave's write command cancels the reply
[your_program] multiBulk has 3 elements
[your_program] SET command is running
[your_program] set command param:bar 456
[your_program] The slave's write command cancels the reply
[your_program] multiBulk has 3 elements
[tester::#YG4] [test] client: Received bytes: "$-1\r\n"
[tester::#YG4] [test] client: Received RESP null bulk string: "$-1\r\n"
[your_program] SET command is running
[your_program] set command param:baz 789
[your_program] The slave's write command cancels the reply
[tester::#YG4] [test] Retrying... (1/5 attempts)
[tester::#YG4] [test] client: > GET foo
[tester::#YG4] [test] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:foo
[your_program] value is 123
[your_program] build response $3
[your_program] 123
[your_program]
[tester::#YG4] [test] client: Received bytes: "$3\r\n123\r\n"
[tester::#YG4] [test] client: Received RESP bulk string: "123"
[tester::#YG4] [test] Received "123"
[tester::#YG4] [test] Getting key bar
[tester::#YG4] [test] client: > GET bar
[tester::#YG4] [test] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$3\r\nbar\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:bar
[your_program] value is 456
[tester::#YG4] [test] client: Received bytes: "$3\r\n456\r\n"
[tester::#YG4] [test] client: Received RESP bulk string: "456"
[your_program] build response $3
[your_program] 456
[your_program]
[tester::#YG4] [test] Received "456"
[tester::#YG4] [test] Getting key baz
[tester::#YG4] [test] client: > GET baz
[tester::#YG4] [test] client: Sent bytes: "*2\r\n$3\r\nGET\r\n$3\r\nbaz\r\n"
[your_program] multiBulk has 2 elements
[your_program] GET command is running
[your_program] get command param:baz
[your_program] value is 789
[your_program] build response $3
[your_program] 789
[your_program]
[tester::#YG4] [test] client: Received bytes: "$3\r\n789\r\n"
[tester::#YG4] [test] client: Received RESP bulk string: "789"
[tester::#YG4] [test] Received "789"
[tester::#YG4] Test passed.
[tester::#YG4] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#YG4] Program terminated successfully
[tester::#HD5] Running tests for Stage #HD5 (Replication - Multi Replica Command Propagation)
[tester::#HD5] $ ./spawn_redis_server.sh --port 6379
[your_program] not found rdb properties
[tester::#HD5] [handshake] Creating replica: 1
[tester::#HD5] [handshake] replica-1: $ redis-cli PING
[tester::#HD5] [handshake] replica-1: Sent bytes: "*1\r\n$4\r\nPING\r\n"
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49480
[your_program] client conn：IP:/0:0:0:0:0:0:0:1, port:49484
[your_program] multiBulk has 1 elements
[your_program] command PING is not exist
[your_program] response is null
[tester::#HD5] Received: "" (no content received)
[tester::#HD5]            ^ error
[tester::#HD5] Error: Expected start of a new RESP2 value (either +, -, :, $ or *)
[tester::#HD5] Test failed
[tester::#HD5] Terminating program
[your_program] Exception in thread "Thread-0" java.lang.IllegalArgumentException: data is null
[your_program] 	at com.zyf.Protocol.readByte(Protocol.java:102)
[your_program] 	at com.zyf.Protocol.process(Protocol.java:22)
[your_program] 	at com.zyf.handle.AbstractHandler.handle(AbstractHandler.java:49)
[your_program] 	at Main.lambda$main$0(Main.java:36)
[your_program] 	at java.base/java.lang.Thread.run(Thread.java:1583)
[tester::#HD5] Program terminated successfully
Read our article on debugging test failures.
Tests failed.
Hide logs
