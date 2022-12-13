package ua.com.prologistic;

import com.guendouz.textclustering.preprocessing.TFidf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    static List<Movie> Read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                "src/ua/com/prologistic/newout.csv"));
        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<Movie> empList = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            Movie emp = new Movie();
            scanner = new Scanner(line);
            scanner.useDelimiter(";");


            while (scanner.hasNext()) {
                String data = scanner.next();
//                System.out.println(index);
//                System.out.println(data);
                if (index == 2)
                    emp.setName(data);
                else if (index == 3)
                    try {
                        emp.setYear(Integer.parseInt(data));
                    } catch (Exception e) {
                        emp.setYear(0);
                    }
                else if (index == 6)
                    emp.setGenre(data);
                else if (index == 7)
                    try {
                        emp.setRating(Float.parseFloat(data));
                    }catch (Exception e){
                        emp.setRating(0.0F);
                    }
                else if (index == 8)
                    emp.setOverview(data);
                else if (index == 10)
                    emp.setDirector(data);
                index++;
            }
            index = 0;
            empList.add(emp);
        }

        //закрываем наш ридер
        reader.close();

        return empList;
    }



    public static void main(String[] args) throws IOException {


        List<Movie> empList = Read();
//        System.out.println(empList);

        HashSet<String> allGene = new HashSet<>();

        for (Movie i : empList) {
//            System.out.println(i.getGenre());
            for (String j : i.getGenre().split(" ")) {
                allGene.add(j.replaceAll("\\p{Punct}", ""));
            }
        }

        Scanner in = new Scanner(System.in);

        System.out.print("Hello!\nIt is micro serves for choosing film on english.\n " +
                "You can improve your skills by watching specially selected for you movie.\n" +
                "If you want to watch certain genre chose one or a few in list below (write them in console, otherwise write - 'no')\n\n");

        System.out.println(allGene);

        String gen = in.nextLine();

        List<Movie> empList_sort = new ArrayList<>();

        if (!gen.equals("no")) {
            for (Movie j : empList) {
                {
                    boolean flag = true;
                    for (String i : gen.split(" "))
                        if (j.getGenre().toLowerCase().lastIndexOf(i.toLowerCase()) == -1)
                            flag = false;

                    if (flag)
                        empList_sort.add(j);
                }
            }
            if (empList_sort.isEmpty()) {
                System.out.print("Sorry, there isn't that combination, please try again..");
                return;
            }
        } else{
            for(Movie i : empList)
                empList_sort.add(i);
        }


        Set<Movie> set = new LinkedHashSet<>(empList_sort);
        List<Movie> empList_sortn = new ArrayList<>(set);



        System.out.print("Okay, I get it! Last step, write a few word about film, what you want to watch.\n" +
                "If you don't know what exactly you want write - 'help', and I'll show the newest ant best!\n");


        String desc = in.nextLine();

        List<List<String>> documents = null;
        if (desc.equals("help")) {
            Comparator<Movie> comparator = (o1, o2) -> {
                if (o1.getRating() == o2.getRating())
                    return o1.getYear().compareTo(o2.getYear());
                else return o1.getRating() > o2.getRating() ? 1 : 0;
            };
            empList_sortn.sort(comparator.reversed());
            System.out.println(empList_sortn);
            return;
        }
        else {
            documents = new ArrayList<>();
            for (Movie j : empList_sortn) {
                List<String> doc = new ArrayList<>();
                String fullstr = j.getName().toLowerCase() + " " +
                        j.getOverview().toLowerCase() + " " + j.getDirector().toLowerCase();
                fullstr = fullstr.replaceAll("\\p{Punct}", "");
                for (String i : fullstr.split(" "))
                    doc.add(i);

                documents.add(doc);
            }
            List<String> doc = new ArrayList<>();
            desc = desc.replaceAll("\\p{Punct}", "");
            for (String i : desc.split(" "))
                doc.add(i);

            documents.add(doc);

//            System.out.println(documents);

            TFidf calculator = new TFidf();

            List<Double> query = new ArrayList<>();
            desc = desc.replaceAll("\\p{Punct}", "");

            for (String i : desc.split(" ")){
                query.add(calculator.tfIdf(documents.get(documents.size() - 1), documents, i));}
//            System.out.println(empList_sortn.size());

            Map<Double, Integer> states = new HashMap<Double, Integer>();

            for (int k = 0; k < empList_sortn.size(); k++) {
                List<Double> doc1 = new ArrayList<>();
                String fullstr = empList_sortn.get(k).getName().toLowerCase() + " " +
                        empList_sortn.get(k).getOverview().toLowerCase() + " " + empList_sortn.get(k).getDirector().toLowerCase();
                fullstr.replaceAll("\\p{Punct}", "");
                for (String i : fullstr.split(" ")) {
                    doc1.add(calculator.tfIdf(documents.get(k), documents, i));
                }
//                System.out.println(doc1);


                double sum = 0.0, sum_a = 0, sum_b = 0, ans = 0;
                for (int i = 0; i < Math.min(doc1.size(), query.size()); i++) {
                    sum += doc1.get(i) * query.get(i);
                    sum_a += doc1.get(i) * doc1.get(i);
                    sum_b += query.get(i) * query.get(i);
                    double val = Math.sqrt(sum_a) * Math.sqrt(sum_b);
                    ans = sum / val;
                }


                states.put(ans, k);
            }


            Map<Double, Integer> treeMap = new TreeMap<Double, Integer>(states);
//            System.out.println(treeMap);


            Collection<Integer> keyVal = treeMap.values();

            for(int i=keyVal.size()-1; i>=0; i--){
                System.out.println(empList_sortn.get(i));
            }
        }




    }
}


