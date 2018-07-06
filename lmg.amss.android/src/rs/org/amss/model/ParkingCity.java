package rs.org.amss.model;

import java.util.ArrayList;

import android.content.Context;
import rs.org.amss.R;

public class ParkingCity {
	public String name;
	public ArrayList<String> zone = new ArrayList<String>();
	public Integer[] icons;
	public String[] brojevi;
	public String[] info;
	public String[] price;
	public String[] maxTime;

	public ParkingCity() { }
	
	public ParkingCity(ArrayList<String> zone, String[] brojevi, Integer[] icons, String name) {
		this.name = name;
		this.zone = zone;
		this.brojevi = brojevi;
		this.icons = icons;
	}
	
	public static ParkingCity get_parking_Beograd(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] beograd_zone = {
				R.string.parking_zone1, 
				R.string.parking_zone2, 
				R.string.parking_zone3, 
				R.string.parking_zones_bez_ogranicenja, 
				R.string.parking_zones_bez_ogranicenja_dnevna,
				R.string.parking_zones_blok_42,
				R.string.parking_zones_blok_42_visesatna,
				R.string.parking_zones_sava_promenada,
				R.string.parking_zones_sava_promenada_visesatna};
		Integer[] beograd_images = {
				R.drawable.zona1, 
				R.drawable.zona2, 
				R.drawable.zona3, 
				R.drawable.dnevna_karta, 
				R.drawable.dnevna_karta,
				R.drawable.dnevna_karta,
				R.drawable.dnevna_karta,
				R.drawable.dnevna_karta,
				R.drawable.dnevna_karta};
		String[] beograd_brojevi  = { "9111", "9112", "9113", "9119", "9118", "8115", "8116", "8117", "8110"};

		temp.zone = addListToArray(ctx, beograd_zone);
		temp.brojevi = beograd_brojevi;
		temp.icons = beograd_images;
		temp.name = ctx.getString(R.string.beograd);
		
		return temp;
	}
	
	public static ParkingCity get_parking_Kragujevac(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] kragujevac_zone = {R.string.parking_zone0, R.string.parking_zone01, R.string.parking_zone01_visecasovna, R.string.parking_zone02, R.string.parking_zone02_visecasovna};
		Integer[] kragujevac_images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta, R.drawable.dnevna_karta };
		String[] kragujevac_brojevi  = { "8340", "8341", "8343", "8342", "8344"};

		temp.zone = addListToArray(ctx, kragujevac_zone);
		temp.brojevi = kragujevac_brojevi;
		temp.icons = kragujevac_images;
		temp.name = ctx.getString(R.string.kragujevac);
		
		return temp;
	}
	
	public static ParkingCity get_parking_NoviSad(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] novisad_zone = {R.string.parking_zones_crvena_zona, R.string.parking_zones_plava_zona, R.string.parking_zones_bela_zona, R.string.parking_daily, R.string.parking_zones_strand_zona, R.string.parking_zones_najlon_zona, R.string.parking_zones_sajam};
		String[] novisad_brojevi  = { "8211", "8212", "8218", "8215", "8213", "8214", "8288"};
		Integer[] novisad_images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta, R.drawable.dnevna_karta, R.drawable.dnevna_karta, R.drawable.dnevna_karta };

		temp.zone = addListToArray(ctx, novisad_zone);
		temp.brojevi = novisad_brojevi;
		temp.icons = novisad_images;
		temp.name = ctx.getString(R.string.novisad);
		
		return temp;
	}
	public static ParkingCity get_parking_Kruševac(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] krusevac_zone = {R.string.parking_zone1, R.string.parking_zone2, R.string.parking_zone3, R.string.parking_daily_all_zones};
		String[] krusevac_brojevi  = { "9371", "9372", "9373", "9370"};
		Integer[] krusevac_images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta };

		temp.zone = addListToArray(ctx, krusevac_zone);
		temp.brojevi = krusevac_brojevi;
		temp.icons = krusevac_images;
		temp.name = ctx.getString(R.string.krusevac);
		
		return temp;
	}
	
	public static ArrayList<String> addListToArray(Context context, int[] items){
		ArrayList<String> result = new ArrayList<String>();
		for (int i=0; i<items.length;i++){
			String item;
			item = context.getString(items[i]);
			result.add(item);
		}
		return result;
	}
	
	public static ParkingCity get_parking_Subotica(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] subotica_zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zuta, R.string.parking_zones_zona3_zelena, R.string.parking_zones_zona4_plava, R.string.parking_zones_zona1_poslovna, R.string.parking_zones_zona2_zona4};
		String[] subotica_brojevi  = { "9241", "9242", "9243", "9244", "9245", "9246"};
		Integer[] subotica_images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta, R.drawable.zona1,  R.drawable.zona2 };

		temp.zone = addListToArray(ctx, subotica_zone);
		temp.brojevi = subotica_brojevi;
		temp.icons = subotica_images;
		temp.name = ctx.getString(R.string.subotica);
		
		return temp;
	}
	
	public static ParkingCity get_parking_Niš(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] nis_zone = {R.string.parking_zones_ekstra_zona, R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna};
		String[] nis_brojevi  = { "9180", "9181", "9184", "9182", "9185" };
		Integer[] nis_images = { R.drawable.dnevna_karta, R.drawable.zona1, R.drawable.zona1, R.drawable.zona3, R.drawable.zona3 };

		temp.zone = addListToArray(ctx, nis_zone);
		temp.brojevi = nis_brojevi;
		temp.icons = nis_images;
		temp.name = ctx.getString(R.string.nis);
		
		return temp;
	}
	
	public static ParkingCity get_parking_Valjevo(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] valjevo_zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_plava, R.string.parking_zones_plava_dnevna};
		String[] valjevo_brojevi  = { "9141", "9142", "9140"};
		Integer[] valjevo_images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.zona3};

		temp.zone = addListToArray(ctx, valjevo_zone);
		temp.brojevi = valjevo_brojevi;
		temp.icons = valjevo_images;
		temp.name = ctx.getString(R.string.valjevo);
		
		return temp;
	}

	public static ParkingCity get_parking_Šabac(Context ctx){
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9151", "9152", "9150" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Senta");
	}
	
	public static ParkingCity get_parking_Aranđelovac(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] arandjelovac_zone = {R.string.parking_zones_crvena_zona, R.string.parking_zones_zuta_zona, R.string.parking_zones_zuta_dnevna_zona};
		String[] arandjelovac_brojevi  = { "9341", "9342", "9343"};
		Integer[] arandjelovac_images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2};

		temp.zone = addListToArray(ctx, arandjelovac_zone);
		temp.brojevi = arandjelovac_brojevi;
		temp.icons = arandjelovac_images;
		temp.name = ctx.getString(R.string.arandjelovac);
		
		return temp;
	}
	
	public static ParkingCity get_parking_Užice(Context ctx){
		ParkingCity temp = new ParkingCity();

		int[] uzice_zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_plava, R.string.parking_zones_zona2_dnevna};
		String[] uzice_brojevi  = { "8311", "8312", "8313"};
		Integer[] uzice_images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta};

		temp.zone = addListToArray(ctx, uzice_zone);
		temp.brojevi = uzice_brojevi;
		temp.icons = uzice_images;
		temp.name = ctx.getString(R.string.uzice);
		
		return temp;
	}

	public static ParkingCity get_parking_Aleksandrovac(Context ctx) {
		ParkingCity temp = new ParkingCity();

		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_plava, R.string.parking_zones_zona2_dnevna};
		String[] brojevi  = { "8371", "8372", "8373"};
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta};

		temp.zone = addListToArray(ctx, zone);
		temp.brojevi = brojevi;
		temp.icons = images;
		temp.name = "Aleksandrovac";
		
		return temp;
	}

	public static ParkingCity get_parking_BačkaPalanka(Context ctx) {
		ParkingCity temp = new ParkingCity();

		int[] zone = {R.string.parking_zones_zuta_zona, R.string.parking_zones_plava_zona, R.string.parking_zones_plava_dnevna };
		String[] brojevi  = { "8421", "8422", "8423" };
		Integer[] images = { R.drawable.zona2, R.drawable.dnevna_karta, R.drawable.dnevna_karta };

		temp.zone = addListToArray(ctx, zone);
		temp.brojevi = brojevi;
		temp.icons = images;
		temp.name = "Bačka Palanka";
		
		return temp;
	}

	public static ParkingCity get_parking_Bečej(Context ctx) {
		int[] zone = {R.string.parking_zones_centralna_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8521", "8522" };
		Integer[] images = { R.drawable.zona3, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Bečej");
	}

	public static ParkingCity get_parking_BelaCrkva(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona1_dnevna_crvena };
		String[] brojevi  = { "7131", "7134" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Bela Crkva");
	}

	public static ParkingCity get_parking_Bujanovac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_dnevna_karta, R.string.parking_zones_zona2_zuta, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9071", "9070", "9072", "9073" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Bujanovac");
	}

	public static ParkingCity get_parking_Čačak(Context ctx) {
		int[] zone = {R.string.parking_zones_ekstra_zona, R.string.parking_zones_zona1_zuta, R.string.parking_zones_zona2_zelena, R.string.parking_zones_zona3_plava, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9320", "9321", "9322", "9323", "9325" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Čačak");
	}

	public static ParkingCity get_parking_Ćuprija(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zuta };
		String[] brojevi  = { "3541", "3542" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Ćuprija");
	}

	public static ParkingCity get_parking_Despotovac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_plava, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8351", "8352", "8353" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Ćuprija");
	}

	public static ParkingCity get_parking_Inđija(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2 };
		String[] brojevi  = { "9221", "9222" };
		Integer[] images = { R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Inđija");
	}

	public static ParkingCity get_parking_Ivanjica(Context ctx) {
		int[] zone = {R.string.parking_zones_zona };
		String[] brojevi  = { "8321" };
		Integer[] images = { R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Ivanjica");
	}

	public static ParkingCity get_parking_Jagodina(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2 };
		String[] brojevi  = { "3501", "3502" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Jagodina");
	}

	public static ParkingCity get_parking_Kikinda(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zuta, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9231", "9232", "9233" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Kikinda");
	}

	public static ParkingCity get_parking_Knjaževac(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_2_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8191", "8192", "8193" };
		Integer[] images = { R.drawable.dnevna_karta, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Knjaževac");
	}

//	public static ParkingCity get_parking_Kruševac(Context ctx) {
//		int[] zone = {R.string.parking_zones_ekstra_zona, R.string.parking_zones_1_zona, R.string.parking_zones_dnevna_karta };
//		String[] brojevi  = { "9371", "9372", "9370" };
//		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
//		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Kruševac");
//	}

	public static ParkingCity get_parking_Kuršumlija(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna_crvena };
		String[] brojevi  = { "9971", "9970" };
		Integer[] images = { R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Kuršumlija");
	}

	public static ParkingCity get_parking_Lazarevac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9281", "9282", "9280" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Lazarevac");
	}

	public static ParkingCity get_parking_Leskovac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8160", "8161", "8161" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Leskovac");
	}

	public static ParkingCity get_parking_Loznica(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9051", "9052", "9050" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Loznica");
	}

	public static ParkingCity get_parking_Negotin(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna_crvena, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna_zuta };
		String[] brojevi  = { "9391", "9390", "9392", "9394" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Negotin");
	}

	public static ParkingCity get_parking_Mladenovac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9821", "9822", "9820" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Mladenovac");
	}

	public static ParkingCity get_parking_NoviPazar(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8631", "8632", "8634" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Novi Pazar");
	}

	public static ParkingCity get_parking_Obrenovac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9871", "9872", "9870" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Obrenovac");
	}

	public static ParkingCity get_parking_Pančevo(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zuta, R.string.parking_zones_zona3_zelena, R.string.parking_zones_zona3_dnevna_zelena };
		String[] brojevi  = { "9131", "9132", "9134", "9133" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona3, R.drawable.zona3, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Pančevo");
	}

	public static ParkingCity get_parking_Paraćin(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona1_dnevna_crvena, R.string.parking_zones_zona2_zuta, R.string.parking_zones_zona2_dnevna_zuta, R.string.parking_zones_zona3_dnevna_zelena };
		String[] brojevi  = { "9351", "9350", "9352", "9354", "9353" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Paraćin");
	}

	public static ParkingCity get_parking_PetrovacNaMlavi(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zelena };
		String[] brojevi  = { "8611", "8612" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Petrovac na Mlavi");
	}

	public static ParkingCity get_parking_Pirot(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_dnevna_karta, R.string.parking_zones_zona2_zelena, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9101", "9100", "9102", "9103" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.zona3, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Pirot");
	}

	public static ParkingCity get_parking_Požarevac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zuta };
		String[] brojevi  = { "8121", "8122" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Požarevac");
	}

	public static ParkingCity get_parking_Požega(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "8441", "8442", "8443" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Požega");
	}

	public static ParkingCity get_parking_Prokuplje(Context ctx) {
		int[] zone = {R.string.parking_zones_ekstra_zona, R.string.parking_zones_zona1_crvena };
		String[] brojevi  = { "9270", "9271" };
		Integer[] images = { R.drawable.dnevna_karta, R.drawable.zona1 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Prokuplje");
	}

	public static ParkingCity get_parking_Ruma(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna, R.string.parking_zones_zona2_putnica_vozila, R.string.parking_zones_zona2_teretna_vozila, R.string.parking_zones_zona3, R.string.parking_zones_zona3_dnevna };
		String[] brojevi  = { "9721", "9722", "9723", "9724", "9725", "9720" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona3, R.drawable.zona3, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Ruma");
	}

	public static ParkingCity get_parking_Raška(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1_crvena, R.string.parking_zones_zona2_zelena, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9461", "9462", "9463" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona3, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Raška");
	}

	public static ParkingCity get_parking_Senta(Context ctx) {	
		int[] zone = {R.string.parking_zones_crvena_zona, R.string.parking_zones_plava_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8241", "8242", "8243" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Senta");
	}

	public static ParkingCity get_parking_Smederevo(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_2_zona, R.string.parking_zones_3_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8661", "8662", "8663", "8664" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Smederevo");
	}

	public static ParkingCity get_parking_Šid(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_2_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8621", "8622", "8623" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Šid");
	}

	public static ParkingCity get_parking_SmederevskaPalanka(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8260", "8261", "8262" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Smederevska Palanka");
	}

	public static ParkingCity get_parking_Sokobanja(Context ctx) {
		int[] zone = {R.string.parking_zones_crvena_zona, R.string.parking_zones_posebna_zona, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9108", "9109", "9110" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona3, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Sokobanja");
	}

	public static ParkingCity get_parking_Sombor(Context ctx) {
		int[] zone = {R.string.parking_zones_ekstra_zona, R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_zona3, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "9250", "9251", "9252", "9253", "9255" };
		Integer[] images = { R.drawable.dnevna_karta, R.drawable.zona1, R.drawable.zona2, R.drawable.zona3, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Sombor");
	}

	public static ParkingCity get_parking_SremskaMitrovica(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna, R.string.parking_zones_zona3, R.string.parking_zones_zona3_dnevna };
		String[] brojevi  = { "8221", "8224", "8222", "8225", "8223", "8226" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2, R.drawable.zona3, R.drawable.zona3 };
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Sremska Mitrovica");
	}

	public static ParkingCity get_parking_SremskiKarlovci(Context ctx) {
		int[] zone = {R.string.parking_zones_sremski_karlovci, R.string.parking_zones_sremski_karlovci_bus };
		String[] brojevi  = { "8261", "8217" };
		Integer[] images = { R.drawable.zona3, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Sremski Karlovci");
	}

	public static ParkingCity get_parking_StaraPazova(Context ctx) {
		int[] zone = {R.string.parking_zones_bela_zona, R.string.parking_zones_crvena_zona };
		String[] brojevi  = { "8201", "8202" };
		Integer[] images = { R.drawable.dnevna_karta, R.drawable.zona1 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Stara Pazova");
	}

	public static ParkingCity get_parking_Surčin(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9081", "9082", "9080" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Surčin");
	}

	public static ParkingCity get_parking_Svilajnac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2 };
		String[] brojevi  = { "3531", "3532" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Svilajnac");
	}

	public static ParkingCity get_parking_Temerin(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona1_dnevna };
		String[] brojevi  = { "8811", "8812" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Temerin");
	}

	public static ParkingCity get_parking_Trstenik(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9471", "9472", "9470" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Trstenik");
	}

	public static ParkingCity get_parking_Topola(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "8444", "8445", "8447" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Topola");
	}

	public static ParkingCity get_parking_Tutin(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1 };
		String[] brojevi  = { "9201" };
		Integer[] images = { R.drawable.zona1 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Tutin");
	}

	public static ParkingCity get_parking_Ub(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_zona2_dnevna };
		String[] brojevi  = { "9041", "9042", "9040" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Ub");
	}

	public static ParkingCity get_parking_VelikoGradište(Context ctx) {
		int[] zone = {R.string.parking_zones_crvena_zona };
		String[] brojevi  = { "8821" };
		Integer[] images = { R.drawable.zona1 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Veliko Gradište");
	}

	public static ParkingCity get_parking_VrnjačkaBanja(Context ctx) {
		int[] zone = {R.string.parking_zones_crvena_zona, R.string.parking_zones_crvena_zona_dnevna, R.string.parking_zones_posebno_parkiraliste, R.string.parking_zones_zelena_zona_dnevna, R.string.parking_zones_ekstra_zona };
		String[] brojevi  = { "9631", "9633", "9632", "9634", "9630" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona3, R.drawable.zona3, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Vrnjačka Banja");
	}

	public static ParkingCity get_parking_Vranje(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_1_zona_dnevna, R.string.parking_zones_2_zona, R.string.parking_zones_2_zona_dnevna, R.string.parking_zones_ekstra_zona, R.string.parking_zones_ekstra_zona_dnevna };
		String[] brojevi  = { "9171", "9170", "9172", "9173", "9174", "9175" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2, R.drawable.dnevna_karta, R.drawable.dnevna_karta };
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Vranje");
	}

	public static ParkingCity get_parking_Vršac(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8131", "8132", "8133" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona2, R.drawable.zona3 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Vršac");
	}

	public static ParkingCity get_parking_Zaječar(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_1_zona_dnevna, R.string.parking_zones_2_zona, R.string.parking_zones_2_zona_dnevna };
		String[] brojevi  = { "9191", "9194", "9192", "9193" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2 };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Zaječar");
	}

	public static ParkingCity get_parking_Zlatibor(Context ctx) {
		int[] zone = {R.string.parking_zones_zona1, R.string.parking_zones_zona2, R.string.parking_zones_dnevna_karta };
		String[] brojevi  = { "8531", "8532", "8533" };
		Integer[] images = { R.drawable.zona1, R.drawable.dnevna_karta, R.drawable.dnevna_karta };		
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Zlatibor");
	}

	public static ParkingCity get_parking_Zrenjanin(Context ctx) {
		int[] zone = {R.string.parking_zones_1_zona, R.string.parking_zones_1_zona_dnevna, R.string.parking_zones_2_zona, R.string.parking_zones_2_zona_dnevna, R.string.parking_zones_3_zona, R.string.parking_zones_3_zona_dnevna };
		String[] brojevi  = { "8230", "8235", "8231", "8236", "8232", "8237" };
		Integer[] images = { R.drawable.zona1, R.drawable.zona1, R.drawable.zona2, R.drawable.zona2, R.drawable.dnevna_karta, R.drawable.dnevna_karta };
		return new ParkingCity(addListToArray(ctx, zone), brojevi, images, "Zrenjanin");
	}
}
