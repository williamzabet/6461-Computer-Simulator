package csci6461Project;

/*Author: Oluwasegun, Ajayi 
* This cache memory implements the LRU (Least Recently Used as it is seen as the most efficient. A small size is also chosen to speed up the hit/miss rate as a 
* bigger cache does not give so much in terms of better efficiency
* 
* Reviewed by: Zabet, William
*/
public class Cache {
   
   /**
    * Using two arrays to store the address and memory separately
    */
   protected int[] cacheAddress = new int[16];
   protected short[] cacheMemory = new short[16];
   
   //Method to store value into cache - only store after you have checked and there is a miss
   public void store(int address, short instruction){
       if(isCacheFull()){
           remove();//remove the least recently used
       }
       cacheAddress[getSize() - 1] = address;
       cacheMemory[getSize() - 1] = instruction;
   }
   
   //Method to check if data is in cache - returns the instruction if the address is in memory and returns -1 if it is not (cache miss)
   public short check(int address){
       short instruction = -1;
       for(int i = getSize() - 1; i >= 0; i--){
           if(cacheAddress[i] == address){
               instruction = cacheMemory[i];
               changePosition(i);
               break;
           }
       }
       
       return instruction;
   }
   
   //Method to check if cache is full
   public boolean isCacheFull(){
       return cacheAddress.length == 16;
   }
   
   //Method to check if cache is empty
   public boolean isCacheEmpty(){
       return cacheAddress.length == 0;
   }
   
   //Method to get size of cache
   public int getSize(){
       return cacheAddress.length;
   }
   
   /**
    * Method to remove data from cache
    * Using the LRU method - we remove the least recently used instruction (i.e. the first data in the array)
    */
   public void remove(){
       if(!isCacheEmpty()){
           for(int i = 0; i < getSize() - 1; i++){
               cacheAddress[i] = cacheAddress[i+1];
               cacheMemory[i] = cacheMemory[i+1];
           }
       }
   }
   
   /**
    * Method that changes the position of the cache data based on recent checks. Once a data is searched for successfully in the array it moves to the top of 
    * the pile
    */
   public void changePosition(int pos){
       int address = cacheAddress[pos];
       short instruction = cacheMemory[pos];
       for(int i = pos; i < getSize() - 1; i++){
           cacheAddress[i] = cacheAddress[i+1];
           cacheMemory[i] = cacheMemory[i+1];
       }
       cacheAddress[getSize() - 1] = address;
       cacheMemory[getSize() - 1] = instruction;
   }
   
   
}
