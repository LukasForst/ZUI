package student;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Class which provide functionality of creating domains.
 */
class DomainFactory {
    /**
     * Creates domain according to the given current data state (data), for the index (idx). Domain won't violate any given constrain (constrains)
     */
    Collection<Character> createDomain(char[] data, int idx, List<Tuple> constrains) {
        Collection<Character> domain = new HashSet<>();
        Iterator<Tuple> blockIterator = constrains.iterator();

        //if there is no constrain, just return '_'
        if (!blockIterator.hasNext()) {
            domain.add('_');
            return domain;
        }

        char previousChar = '\n';
        int currentCharCount = 0;
        Tuple currentBlock = null;

        //iterate through current data state
        for (int i = 0; i < idx && i < data.length; i++) {
            char currentChar = data[i];

            if (currentChar == '_') {
                //these asserts are there just in case my implementation was wrong -> exception will be thrown
                assert previousChar == '_' || currentBlock == null || currentBlock.getBlockSize() == currentCharCount;

                currentBlock = null;
            } else if (currentChar == previousChar) {
                //same char so increase count of chars in the current block
                currentCharCount++;

                //these asserts are there just in case my implementation was wrong -> exception will be thrown
                assert currentBlock != null && currentBlock.getBlockSize() >= currentCharCount;
            } else {
                //these asserts are there just in case my implementation was wrong -> exception will be thrown
                assert previousChar == '_' || currentBlock == null || currentBlock.getBlockSize() == currentCharCount;

                //currentChar is not in the current block so current block size is 1 and we will pop next block
                currentCharCount = 1;
                currentBlock = blockIterator.next();
            }

            previousChar = currentChar;
        }

        //this if statement is used only for checking for constrain violation
        //if we pass index which is not in the array, this method will return null if all constrains are satisfied, otherwise it will return not empty collection
        if (idx >= data.length) {
            if (!blockIterator.hasNext() && (currentBlock == null || currentCharCount == currentBlock.getBlockSize())) {
                return null;
            }
            domain.add('\0');
            return domain;
        }

        //create domain for given variable according to the constrains
        if (currentBlock == null || currentBlock.getBlockSize() == currentCharCount) {
            //there is no constrain or current constrain is satisfied -> whole block is assigned
            domain.add('_');

            //because previous constrain is satisfied we can pop next block
            if (blockIterator.hasNext()) {
                Tuple nextConstrain = blockIterator.next();

                //to satisfy constrain #1 (between blocks with same color has to be '_') we have to check next block color
                if (currentBlock == null || currentBlock.getColor() != nextConstrain.getColor()) {
                    //if it is different color, we can add new color to the domain
                    domain.add(nextConstrain.getColor());
                }
            }
        } else if (previousChar == currentBlock.getColor() && currentBlock.getBlockSize() > currentCharCount) {
            //this case is case when constrain #4 has to be satisfied
            //current block is not same sized as it should be, so only same color can be assigned
            domain.add(currentBlock.getColor());
        } else {
            //situation when we are changing color of the block
            domain.add('_');
            domain.add(currentBlock.getColor());
        }

        return domain;
    }
}
