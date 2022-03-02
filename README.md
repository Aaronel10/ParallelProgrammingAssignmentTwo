# ParallelProgrammingAssignmentTwo

Problem One)

I decided to use 3 threads to demonstrate my program.
To solve the problem have one guest be designated the counter of the others.
He will be the only thread that can replace a cake. After that everyone else just needs to wait their turn.
Each guest will wait until they see the cake available and eat it only once no matter how many times they enter the labyrinth.
Then the one counter thread can replace the cake every time he sees its gone. Once hes replaced n-1 cakes that means all the guests have entered.
Since I let all threads overlap they replicate the minotaur randomly picking guests as I can't guarentee which thread will get be able to get the lock.
Every thread an instance variable hasBeenCounted to avoid counting a thread twice. And I use atomic class variables to keep track of the cake and the current count.
I also used javas built in Reentrant Lock to guarentee mutual exclusion.


Problem Two)

The 3rd option for problem 2 consisted of using a queue that has a guest tell the next in line to come in. I chose to use the CLH lock from the textbook. I also just declared 5 threads instead of asking for input.
The reason why I chose the CLH lock was because it was space efficient compared to other locks like the Anderson lock. The algorithm has each thread spin on a distinct location so when one thread
releases the lock it only invalidates its sucessors cache. It also provides first come first serve fairness.
The only disadvantage to the CLH lock is that it performs poorly on NUMA architectures.
