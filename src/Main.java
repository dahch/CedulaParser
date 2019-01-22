import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(parseDataCode("0332988832\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000PubDSK_1\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000717274741098619118CHAJIN\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000ORTIZ\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000ROBERTO\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00000M19860603210250O+\u00002\u0002C\u0000\u009E[ÿ\u0080\u0080z[\u0085}\u0091\u0083ub\u0086\u0086\u0082\u0091\u0086S^\u0081gpn\u008E?\u008A3t]d;eÐWÁX¶\u0087½LÂ6Ãs LLK¢_¨G´\u007F\u00ADu±0±:H?°&¦%y*\u009C! 5 CN\u009Da·w\u009C4\u0080P{^pfk¨\u009D5gX_\u0090[«±\u00AD¢¹ \u009AbÂ\u0091Â°9I\u0094j]S\u008CrlTw9lXwD\u008E+\u009C\u0084\u0086G\u0094z\u008BK\u0085t{nØ\u0012Í~\u0085Bdáÿ#lØÅ¼\u0012Rý\u00007\u0002C\u0000©]ÿ\u0080\u0080\u0081v\u0098À\u0084\u0081\u0081¶\u008DÄ¢Å\u007FÔ¦ix¸~\u008F\u0090\u0082\u0095\u0087gÆ\u0091\u0091IµÉqY¥\u0085\u0097¹o´\u0099±¡¦ ÂN;\u0085¼NÅ¯»º]\u008EªG\u0096E\\eNYt\u008C|W¬m\u008D\u0087´wXÄ´m·\u0082²\u009BÈhÉ}Þ\u0084½¥Ê\u008EÆ\u0097Ò£C\u008E©°A\u0084J\u0087\u009AJ¥ÀGZh]j\u008C\u0094¹£bmmNSlv\u0084ª\u0092dw¡\u007F\u00AD\u0083k\u0014IåDKiÛÅ_¯\u0014©ìö\u0082Bü\u0000RÒ0-ó3K\u0017\u0089>Î\u007Fx/\u0015ù·v\u000B(P\u000Ec,sr¼\u0004í\u009AT¤\u0011Ê\u0091>ó(ÔX").toString());
    }

    public static InfoCedula parseDataCode(String barcode) {
        InfoCedula infoCedula = null;
        if (barcode != null) {

            String barCode = barcode;
            //Log.d(TAG, "Barcode length: " + barcode.displayValue.length());
            if (barcode.length() < 150) {
                //TODO lanzar excepcion y mensaje
                return infoCedula;
            }

            infoCedula = new InfoCedula();
            String primerApellido = "", segundoApellido = "", primerNombre = "", segundoNombre = "", cedula = "", rh = "", fechaNacimiento = "", sexo = "";

            String alphaAndDigits = barCode.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_]+", " ");
            String[] splitStr = alphaAndDigits.split("\\s+");
            /*
            for (int i=0; i<splitStr.length;i++){
                Log.d(TAG, i + "valor: " + splitStr[i]);
            }
            */
            if (!alphaAndDigits.contains("PubDSK")) {
                int corrimiento = 0;


                Pattern pat = Pattern.compile("[A-Z]");
                Matcher match = pat.matcher(splitStr[2 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();
                    System.out.println("match.start: " + match.start());
                    System.out.println("match.end: " + match.end());
                    System.out.println("splitStr: " + splitStr[2 + corrimiento]);
                    System.out.println("splitStr length: " + splitStr[2 + corrimiento].length());
                    System.out.println("lastCapitalIndex: " + lastCapitalIndex);
                }
                cedula = splitStr[2 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[2 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[3 + corrimiento];
                primerNombre = splitStr[4 + corrimiento];
                /**
                 * Se verifica que contenga segundo nombre
                 */
                if (Character.isDigit(splitStr[5 + corrimiento].charAt(0))) {
                    corrimiento--;
                } else {
                    segundoNombre = splitStr[5 + corrimiento];
                }

                sexo = splitStr[6 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                rh = splitStr[6 + corrimiento].substring(splitStr[6 + corrimiento].length() - 2);
                fechaNacimiento = splitStr[6 + corrimiento].substring(2, 10);

            } else {
                int corrimiento = 0;
                Pattern pat = Pattern.compile("[A-Z]");
                if (splitStr[2 + corrimiento].length() > 7) {
                    corrimiento--;
                }


                Matcher match = pat.matcher(splitStr[3 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();

                }

                cedula = splitStr[3 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[3 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[4 + corrimiento];
                primerNombre = splitStr[5 + corrimiento];
                if (splitStr[6 + corrimiento].charAt(0) == '0') {
                    sexo = splitStr[6 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                    rh = splitStr[6 + corrimiento].substring(splitStr[6 + corrimiento].length() - 2);
                    fechaNacimiento = splitStr[6 + corrimiento].substring(2, 10);
                } else {
                    segundoNombre = splitStr[6 + corrimiento];
                    sexo = splitStr[7 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                    rh = splitStr[7 + corrimiento].substring(splitStr[7 + corrimiento].length() - 2);
                    fechaNacimiento = splitStr[7 + corrimiento].substring(2, 10);
                }

            }
            /**
             * Se setea el objeto con los datos
             */
            infoCedula.setPrimerNombre(primerNombre);
            infoCedula.setSegundoNombre(segundoNombre);
            infoCedula.setPrimerApellido(primerApellido);
            infoCedula.setSegundoApellido(segundoApellido);
            infoCedula.setCedula(Integer.valueOf(cedula).toString());
            infoCedula.setSexo(sexo);
            infoCedula.setFechaNacimiento(fechaNacimiento);
            infoCedula.setRh(rh);


        } else {
            System.out.println("No barcode capturado");
            return infoCedula;
        }

        return infoCedula;
    }
}
