package com.example.learnit.topics;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class TopicsResourceParsers {
    /*
    * Пробегаемся по всему документу xml и получаем его содержимое
    * Где считываемый тег равен выбранному топику
    * */
    public static List<Topic> parse(XmlPullParser xpp, String selectedTopic) {
        List<Topic> topics = new ArrayList<>();
        Topic currentTopic = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            //получаем первое событие и потом последовательно считываем документ, пока не дойдем до его конца
            int eventType = xpp.getEventType();
            //Когда будет достигнут конец документа, то событие будет представлять константу END_DOCUMENT
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();//название считываемого элемента
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if(selectedTopic.equalsIgnoreCase(tagName)){
                            // устанавливаем, что мы находимся внутри элемента selectedTopic (например, <animal>)
                            inEntry = true;
                            currentTopic = new Topic();
                        }
                        break;
                    //Если событие TEXT, то считано содержимое элемента
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    /*
                    * Если закрывающий тег, то все зависит от того, какой элемент прочитан.
                    * Если прочитан элемент selectedTopic, то добавляем объект Topic в коллекцию
                    * и сбрываем переменную inEntry, указывая, что мы вышли из элемента selectedTopic
                    * Если прочитаны элементы question, options и answer,
                    * то передаем их значения переменным объекта Topic:
                    * */
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if(selectedTopic.equalsIgnoreCase(tagName)){
                                topics.add(currentTopic);
                                inEntry = false;
                            } else if("question".equalsIgnoreCase(tagName)){
                                currentTopic.setQuestion(textValue);
                            } else if("options".equalsIgnoreCase(tagName)){
                                String[] options = textValue.split(",");
                                currentTopic.setOption1(options[0]);
                                currentTopic.setOption2(options[1]);
                                currentTopic.setOption3(options[2]);
                                currentTopic.setOption4(options[3]);
                            } else if("answer".equalsIgnoreCase(tagName)){
                                currentTopic.setAnswer(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next(); //переход к следующему событию
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return topics;
    }
}
