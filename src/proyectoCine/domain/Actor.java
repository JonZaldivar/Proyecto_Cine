package proyectoCine.domain;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class Actor {
	
	public enum Pais {
	    AFGANISTAN, ALBANIA, ALEMANIA, ANDORRA, ANGOLA, ANTIGUA_Y_BARBUDA,
	    ARABIA_SAUDITA, ARGELIA, ARGENTINA, ARMENIA, AUSTRALIA, AUSTRIA,
	    AZERBAIYAN, BAHAMAS, BANGLADES, BARBADOS, BAHREIN, BELGICA,
	    BELICE, BENIN, BIELORRUSIA, BOLIVIA, BOSNIA_HERZEGOVINA, BOTSUANA,
	    BRASIL, BRUNEI, BULGARIA, BURKINA_FASO, BURUNDI, CABO_VERDE,
	    CAMBOYA, CAMERUN, CANADA, CATAR, CHAD, CHILE,
	    CHINA, CHIPRE, COLOMBIA, COMORAS, CONGO, COREA_DEL_NORTE,
	    COREA_DEL_SUR, COSTA_DE_MARFIL, COSTA_RICA, CROACIA, CUBA, DINAMARCA,
	    DOMINICA, ECUADOR, EGIPTO, EL_SALVADOR, EMIRATOS_ARABES_UNIDOS, ERITREA,
	    ESLOVAQUIA, ESLOVENIA, ESPAÑA, ESTADOS_UNIDOS, ESTONIA, ETIOPIA,
	    FILIPINAS, FINLANDIA, FRANCIA, GABON, GAMBIA, GEORGIA,
	    GHANA, GRANADA, GRECIA, GUATEMALA, GUINEA, GUINEA_ECUATORIAL,
	    GUINEA_BISSAU, GUYANA, HAITI, HONDURAS, HUNGRIA, INDIA,
	    INDONESIA, IRAK, IRAN, IRLANDA, ISLANDIA, ISRAEL,
	    ITALIA, JAMAICA, JAPON, JORDANIA, KAZAJISTAN, KENIA,
	    KIRGUISTAN, KIRIBATI, KUWAIT, LAOS, LESOTO, LETONIA,
	    LIBANO, LIBERIA, LIBIA, LIECHTENSTEIN, LITUANIA, LUXEMBURGO,
	    MADAGASCAR, MALASIA, MALAWI, MALDIVAS, MALI, MALTA,
	    MARRUECOS, MAURICIO, MAURITANIA, MEXICO, MICRONESIA, MOLDAVIA,
	    MONACO, MONGOLIA, MONTENEGRO, MOZAMBIQUE, NAMIBIA, NAURU,
	    NEPAL, NICARAGUA, NIGER, NIGERIA, NORUEGA, NUEVA_ZELANDA,
	    OMAN, PAISES_BAJOS, PAKISTAN, PALAOS, PANAMA, PAPUA_NUEVA_GUINEA,
	    PARAGUAY, PERU, POLONIA, PORTUGAL, REINO_UNIDO, REPUBLICA_CHECA,
	    REPUBLICA_CENTROAFRICANA, REPUBLICA_DEMACEDONIA, REPUBLICA_DEMOCRATICA_DEL_CONGO, REPUBLICA_DOMINICANA, RUANDA, RUMANIA,
	    RUSIA, SAMOA, SAN_CRISTOBAL_Y_NIEVES, SAN_MARINO, SAN_VICENTE_Y_LAS_GRANADINAS, SANTA_LUCIA,
	    SANTO_TOME_Y_PRINCIPE, SENEGAL, SERBIA, SEYCHELLES, SIERRA_LEONA, SINGAPUR,
	    SIRIA, SOMALIA, SRI_LANKA, SUDAFRICA, SUDAN, SUDAN_DEL_SUR,
	    SUECIA, SUIZA, SURINAM, TAILANDIA, TANZANIA, TAYIKISTAN,
	    TIMOR_ORIENTAL, TOGO, TONGA, TRINIDAD_Y_TOBAGO, TUNEZ, TURKMENISTAN,
	    TURQUIA, TUVALU, UCRANIA, UGANDA, URUGUAY, UZBEKISTAN,
	    VANUATU, VATICANO, VENEZUELA, VIETNAM, YEMEN, ZAMBIA,
	    ZIMBABUE;
	}

	
	private int id;
	private String nombre;
	private LocalDate fechaNacimiento;
	private Pais nacionalidad;
	
	public Actor(int id, String nombre, LocalDate fechaNacimiento, Pais nacionalidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
	}
	
	public Actor() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public String getFechaNacimientoStr() {
		return fechaNacimiento.toString();
	}



	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setFechaNacimientoStr(String fechaNacimiento) {
	    try {
	        
	        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
	    } catch (DateTimeParseException e) {
	        
	        throw new IllegalArgumentException("Formato de fecha inválido: " + fechaNacimiento, e);
	    }
	}

	public Pais getPais() {
		return nacionalidad;
	}
	
	public void setPais(Pais nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Actor [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", nacionalidad="
				+ nacionalidad + "]";
	}
	
	
	
	
	
}
