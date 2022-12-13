package project.flashCards.recomendationSystem;

import project.flashCards.config.Config;

import java.time.LocalDateTime;
import java.util.List;

/**Рекомендательная система*/
public class RecommendationSystem{
    /**
     * Метод getRecommendation позволяет узнать рекомендацию системы насчёт групп,
     * которые следует сегодня повторить.
     **/
    public static void getRecommendation(){
        int day = LocalDateTime.now().getDayOfMonth();
        List<Integer> frequency = Config.getFREQUENCY();
        for (int i = 0; i < Config.getGroupNumber(); i++){
            if (day % frequency.get(i) == 0){
                System.out.println("Вам следует повторить группу №" + (i+1));
            }
        }
    }
}