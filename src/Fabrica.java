
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fabrica {

    public static ArrayList<Assignatura> carregaAssig(String archivo) throws IOException, ParseException {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat(archivo);
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(filePath));
        // typecasting obj to JSONObject
        JSONObject assigFile = (JSONObject) obj;

        JSONArray llista = (JSONArray) assigFile.get("Assignatures");

        ArrayList<Assignatura> vassig = new ArrayList<>();
        
        for(int i = 0; i < llista.size(); ++i){
            String nomAssig = (String) ((JSONObject) llista.get(i)).get("codiAssig");
            String codiBloc = (String) ((JSONObject) llista.get(i)).get("quatri");
            int numeroAlumnes = ((Long) ((JSONObject) llista.get(i)).get("alumnes")).intValue();
            //System.out.println(nomAssig + " pertany a bloc " + codiBloc);
            /*JSONArray llistaGrups = (JSONArray) ((JSONObject) llista.get(i)).get("grups");
            ArrayList<Integer> grups = new ArrayList<>();
            for(int j = 0; j < llistaGrups.size(); ++j) {
                grups.add(((Long) llistaGrups.get(j)).intValue());
                //System.out.print(" " + ((Long) llistaGrups.get(j)).intValue());
            }
            //System.out.println();*/


            vassig.add(new Assignatura(nomAssig, codiBloc, numeroAlumnes));
        }
        return vassig;
    }

    public static ArrayList<Aula> carregaAules(String archivo) throws IOException, ParseException {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat(archivo);
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(filePath));
        // typecasting obj to JSONObject
        JSONObject aulesFile = (JSONObject) obj;

        JSONArray llista = (JSONArray) aulesFile.get("Aules");

        ArrayList<Aula> vaula = new ArrayList<>();

        for(int i = 0; i < llista.size(); ++i){
            String aulari = (String) ((JSONObject) llista.get(i)).get("aulari");
            String pis = (String) ((JSONObject) llista.get(i)).get("pis");
            int numero = ((Long) ((JSONObject) llista.get(i)).get("numero")).intValue();
            int capacitat = ((Long) ((JSONObject) llista.get(i)).get("capacitat")).intValue();
            //System.out.println(aulari + pis + String.format("%02d", numero) + " amb capacitat: "+capacitat);
            vaula.add(new Aula(aulari, pis.charAt(0), numero, capacitat));
        }
        return vaula;
    }
}
