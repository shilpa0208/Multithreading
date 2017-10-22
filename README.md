# Multithreading

An introduction to all multithreading concepts with lower and higher level constructs in Java. Comments have been included indicating important concepts.

#### Lower Level Constructs include :
 * wait() and notify()
 * synchronized keyword
 * Using locks on objects 

#### Higher Level Constructs include:
  * Semaphores
  * Callable and Futures
  * Countdown Latch
  * Reentrant Lock
  * Thread Pools using ExecutorService
  * Blocking Queues 


#### It includes a demo of the following concepts :
  * The usage of synchronized keyword.
  * Benefits of obtaining multiple locks with different locks on the same object.
  * Creating Thread Pools and the important functions such as shutdown() and await().
  * CountDown Latches - higher level construct whch prevents using low level constructs such as wait(), notify(), synchronized() etc.
  * Blocking Queues which uses the Producer Consumer pattern to achieve certain tasks using take() and put() functions.
  * Usage of lower level constructs such as wait() and notify().
  * Re-entrant locks along with conditions to demonstrate signal() and await().
  * A working demo of Deadlock and how tryLock() of Reentrant Lock can resolve deadlocks.
  * Usage of Semaphore a higher level construct along with the release() and acquire() methods.
  * Callable and Futures introduced in Java 8 which helps handles exceptions that are thrown and return values from threads.
  * A final showcase of handling execptions on our own by creating appropriate handlers along with Futures.
