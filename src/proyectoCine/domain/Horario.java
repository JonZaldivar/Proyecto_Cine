package proyectoCine.domain;

public enum Horario {
	H0900, H1130, H1400, H1630, H1900, H2130;

    @Override
    public String toString() {
        switch (this) {
            case H0900: return "09:00";
            case H1130: return "11:30";
            case H1400: return "14:00";
            case H1630: return "16:30";
            case H1900: return "19:00";
            case H2130: return "21:30";
            default: return "";
        }
    }
    
    
}
