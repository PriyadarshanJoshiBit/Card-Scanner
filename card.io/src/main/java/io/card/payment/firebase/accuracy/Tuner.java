package io.card.payment.firebase.accuracy;

import java.net.FileNameMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Tuner {

    private HashMap<String,Integer> cardNumAccMap = new HashMap<String,Integer>();
    private HashMap<String,Integer> expDtAccMap = new HashMap<String,Integer>();
    private HashMap<String,Integer> cvvAccMap = new HashMap<String,Integer>();

    private static final Tuner ourInstance = new Tuner();

    static Tuner getInstance() {
        return ourInstance;
    }

    private Tuner() {
    }


    public void addCardNumber(String cardNumber){
        addEntityToMap(cardNumAccMap,cardNumber);
    }

    public void addExpDt(String expDt){
        addEntityToMap(expDtAccMap,expDt);
    }

    public void addCVV(String cvv){
        addEntityToMap(cvvAccMap,cvv);
    }


    public void nullify(){
        this.clearMaps();
    }

    public String mostlyOccuringCardNumber(){
        return cardNumAccMap.isEmpty() ? null : mostlyOccurringEntity(cardNumAccMap);
    }
    public String mostlyOccuringCVV(){
        return cvvAccMap.isEmpty() ? null : mostlyOccurringEntity(cvvAccMap);
    }
    public String mostlyOccuringExpiryDate(){
        return expDtAccMap.isEmpty() ? null : mostlyOccurringEntity(expDtAccMap);
    }

    private void clearMaps(){
        cardNumAccMap.clear();
        expDtAccMap.clear();
        cvvAccMap.clear();
    }



    private void addEntityToMap(HashMap<String,Integer> accurcyMap, String entity){
        if(!(entity == null ||  entity.equals(""))){
            int currentCount = accurcyMap.get(entity) == null ? 0 : accurcyMap.get(entity) ;
            accurcyMap.put(entity, currentCount + 1 );
        }
    }


    private String mostlyOccurringEntity(HashMap<String,Integer> accurcyMap){

        String mostFreqAccNum = "";
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(accurcyMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        return ((LinkedList<Map.Entry<String,Integer>>) list).getFirst().getKey();
    }

    private String

}
