public class RestHoraAssig extends ResAssig{
	
	//Hora en la que NO vols que es faci classe
	
    private int hora;
    
// @Override a sobre de la funcio que vull sobreescriure

    public RestHoraAssig(Assignatura assig, int hora) {
        this.assig = assig;
        this.hora = hora;
    }
    
    @Override
    public boolean esCompleix(Classe classe)
	{
	    System.out.println("Soc el net: restHoraAssig");
	    boolean ans = false;
	    if (classe.getHoraIni()!=hora) ans=true;
		return ans;
	}
    
}