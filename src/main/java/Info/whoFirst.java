package Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class whoFirst {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        Collections.shuffle(numbers);

        System.out.println("Группы в случайном порядке:");
        for (int num : numbers) {
            System.out.println(num);
        }
    }
}
