package student;

import java.util.*;

public class DomainFactory {
    public Collection<Character> createDomain(char[] data, int idx, List<Tuple> constrains) {
        Collection<Character> domain = new HashSet<>();
        Iterator<Tuple> constrainsIterator = constrains.iterator();

        if (!constrainsIterator.hasNext()) {
            domain.add('_');
            return domain;
        }

        char previousChar = '\n';
        int currentCharCount = 0;
        Tuple currentConstrain = null;

        for (int i = 0; i < idx && i < data.length; i++) {
            char currentChar = data[i];

            if (currentChar == '_') {
                if (previousChar != '_') {
                    assert currentConstrain == null || currentConstrain.getBlockSize() == currentCharCount;
                }
                currentConstrain = null;
            } else if (currentChar == previousChar) {
                currentCharCount++;
                assert currentConstrain != null;
                assert currentConstrain.getBlockSize() >= currentCharCount;
            } else {
                if (previousChar != '_') {
                    assert currentConstrain == null || currentConstrain.getBlockSize() == currentCharCount;
                }

                currentCharCount = 1;
                currentConstrain = constrainsIterator.next();
            }

            previousChar = currentChar;
        }

        if (idx >= data.length) { //this is only for verification and should not happen
            if (!constrainsIterator.hasNext() && (currentConstrain == null || currentCharCount == currentConstrain.getBlockSize())) {
                return null;
            }
            domain.add('\0');
            return domain;
        }

        if (currentConstrain == null || currentConstrain.getBlockSize() == currentCharCount) {
            domain.add('_');
            if (constrainsIterator.hasNext()) {
                Tuple nextConstrain = constrainsIterator.next();

                if (currentConstrain == null || currentConstrain.getColor() != nextConstrain.getColor()) {
                    domain.add(nextConstrain.getColor());
                }
            }
        } else if (previousChar == currentConstrain.getColor() && currentConstrain.getBlockSize() > currentCharCount) {
            domain.add(currentConstrain.getColor());
        } else {
            domain.add('_');
            domain.add(currentConstrain.getColor());
        }

        return domain;
    }
}
