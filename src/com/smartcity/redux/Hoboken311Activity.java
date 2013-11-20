package com.smartcity.redux;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Hoboken311Activity extends Activity implements OnItemSelectedListener {

	static final private int VOICE_REQUEST = 0;
	private HashMap<String,Integer> tabHash;
	private ArrayList<String> text;
	
	Spinner spn_street;
	Spinner spn_prob;
	Spinner spn_prob_spec;
	Spinner spn_prob_spec2;
	
	Context context;
	
	ArrayAdapter<CharSequence> currentAdapter;
	ArrayAdapter<CharSequence> currentAdapter2;
	
	String request = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hoboken311);
		
		context = this;
		
		
		spn_prob = (Spinner)findViewById(R.id.spn_prob);
		spn_prob_spec = (Spinner)findViewById(R.id.spn_prob_spec);
		spn_prob_spec2 = (Spinner)findViewById(R.id.spn_prob_spec2);
		
		spn_prob.setOnItemSelectedListener(this);
		
		spn_prob_spec.setVisibility(View.GONE);
		spn_prob_spec.setOnItemSelectedListener(this);
		
		spn_prob_spec2.setVisibility(View.GONE);
		spn_prob_spec2.setOnItemSelectedListener(this);
		
		final Toast warning = Toast.makeText(this, "Please Select Your Problem", Toast.LENGTH_SHORT);
		
		ArrayAdapter<CharSequence> adapterProb = ArrayAdapter.createFromResource(this, R.array.spn_prob, android.R.layout.simple_spinner_item );
		adapterProb.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spn_prob.setAdapter(adapterProb);
		
		Button btn_map = (Button)findViewById(R.id.btn_311_map);
		btn_map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			//	Intent i = new Intent(context, MyCity311Map.class);
				
				if(request != null){
				//	i.putExtra("REQUEST", request);
					//startActivity(i);
					finish();
				}else{
					warning.show();
				}
				
			}
		});
		
		
		tabHash = new HashMap<String, Integer>();
		
		tabHash.put("cable", 0);
		
		tabHash.put("animal bite", 1);
		
		tabHash.put("animal carcass in road", 2);
		
		tabHash.put("animal cruelty", 3);
		
		tabHash.put("dead animal", 4);
		
		tabHash.put("dog defecation", 5);
		
		tabHash.put("dog without license", 6);
		
		tabHash.put("feeding pigeons", 7);
		
		tabHash.put("illegal pets or livestock", 8);
		
		tabHash.put("injured pet or animal", 9);
		
		tabHash.put("kennel complaints", 10);
		
		tabHash.put("pigeon droppings", 11);
		
		tabHash.put("rodents and insects", 12);
		
		tabHash.put("stray animal", 13);
		
		tabHash.put("unleashed animal", 14);
		
		tabHash.put("mayor's office", 15);
		
		tabHash.put("website feedback", 16);
		
		tabHash.put("abandoned building", 17);
		tabHash.put("abandoned property", 17);
		
		tabHash.put("property alteration without permit", 18);
		
		tabHash.put("container work without permit", 19);
		
		tabHash.put("sidewalk work without permit", 20);
		
		tabHash.put("street work without permit", 21);
		
		tabHash.put("excessive signage", 22);
		
		tabHash.put("sidewalk cafe complaint", 23);
		
		tabHash.put("dumpster overflow", 24);
		
		tabHash.put("full public waste bin", 25);
		
		tabHash.put("hazardous waste disposal", 26);
		
		tabHash.put("illegal trash/dumping", 27);
		
		tabHash.put("improper storage of trash barrels", 28);
		
		tabHash.put("leaf collection", 29);
		
		tabHash.put("missed trash pickup", 30);
		
		tabHash.put("trash", 31);
		
		tabHash.put("trash on a vacant lot", 32);
		
		tabHash.put("graffiti city property", 33);
		
		tabHash.put("graffiti parking meters", 34);
		
		tabHash.put("graffiti parks", 35);
		
		tabHash.put("graffiti private property", 36);
		
		tabHash.put("illegal posting of flyers", 37);
		
		tabHash.put("recycling missed pickup", 38);
		
		tabHash.put("recycling sticker request", 39);
		
		tabHash.put("request container", 40);
		
		tabHash.put("request public bin installation", 41);
		
		tabHash.put("asbestos", 42);
		
		tabHash.put("bed bugs", 43);
		
		tabHash.put("grease", 44);
		
		tabHash.put("hoarding", 45);
		
		tabHash.put("mold", 46);
		
		tabHash.put("odor complaint", 47);
		
		tabHash.put("restaurant operating without license", 48);
		
		tabHash.put("suspected food poisoning", 49);
		
		tabHash.put("unlicensed vendor", 50);
		
		tabHash.put("unsanitary apartment", 51);
		
		tabHash.put("unsanitary conditions", 52);
		
		tabHash.put("bells not working", 53);
		tabHash.put("intercom not working", 53);
		
		tabHash.put("emergency lighting", 54);
		
		tabHash.put("exterior lighting", 55);
		
		tabHash.put("front door does not close", 56);
		
		tabHash.put("illegal apartment", 57);
		tabHash.put("illegal property", 57);
		
		tabHash.put("illegal sign", 58);
		
		tabHash.put("landlord complaint", 59);
		
		tabHash.put("obstructed sidewalk", 60);
		
		tabHash.put("occupancy without valid c o", 61);
		tabHash.put("occupancy without valid c c o", 61);
		
		tabHash.put("smoke and carbon monoxide detector", 62);
		
		tabHash.put("uncut grass", 63);
		
		tabHash.put("unsafe structure", 64);
		
		tabHash.put("water leaks", 65);
		
		tabHash.put("windows need replacing", 66);
		
		tabHash.put("local access channel", 67);
		
		tabHash.put("animal noise", 68);
		
		tabHash.put("h v a c", 69);
		tabHash.put("stationary equipment noise", 69);
		
		tabHash.put("illegally parked cars", 70);

		tabHash.put("parking lot complaint", 71);
		tabHash.put("garage complaint", 71);
		
		tabHash.put("parking meter malfunction", 72);
		
		tabHash.put("report customer service experience", 73);
		
		tabHash.put("report handicap parking abuse", 74);
		
		tabHash.put("report parking enforcement experience", 75);
		
		tabHash.put("report selective parking enforcement", 76);
		
		tabHash.put("bathroom repair", 77);
		
		tabHash.put("benches", 78);
		
		tabHash.put("landscaping", 79);
		
		tabHash.put("light out", 80);
		
		tabHash.put("painting", 81);
		
		tabHash.put("park cleaning", 82);
		tabHash.put("park litter", 82);
		
		tabHash.put("playground equipment", 83);
		
		tabHash.put("tree work", 84);
		
		tabHash.put("trim hedges", 85);
		tabHash.put("trim weeds", 85);
		
		tabHash.put("vandalism", 86);
		
		tabHash.put("new tree request", 87);
		
		tabHash.put("recommend a tree site", 88);
		
		tabHash.put("request a sidewalk tree pit", 89);
		
		tabHash.put("tree fallen across road", 90);
		
		tabHash.put("tree fallen across sidewalk", 91);
		
		tabHash.put("tree planting", 92);
		
		tabHash.put("tree pruning", 93);
		tabHash.put("tree trimming", 93);
		
		tabHash.put("tree replacement", 94);
		
		tabHash.put("tree removal", 95);
		tabHash.put("stump removal", 95);
		
		tabHash.put("missing traffic sign", 96);
		tabHash.put("damaged traffic sign", 96);
		tabHash.put("missing parking sign", 96);
		tabHash.put("damaged parking sign", 96);
		
		tabHash.put("pavement marking", 97);
		
		tabHash.put("pedstrian signal problem", 98);
		
		tabHash.put("street light post out", 99);
		tabHash.put("street lamp post out", 99);
		
		tabHash.put("traffic signal problem", 100);
		
		tabHash.put("report a biking hazard", 101);
		
		tabHash.put("report abandoned bike", 102);
		
		tabHash.put("report damaged bike rack station", 103);
		tabHash.put("report damaged bike repair station", 103);
		
		tabHash.put("request a bike rack", 104);
		
		tabHash.put("corner car complaint", 105);
		
		tabHash.put("h o p", 106);
		tabHash.put("senior shuttle complaint", 106);
		
		tabHash.put("broken sidewalk", 107);
		
		tabHash.put("curb missing paint", 108);
		
		tabHash.put("pothole", 109);
		
		tabHash.put("crossing guards", 110);
		
		tabHash.put("pedestrian safety concern", 111);
		tabHash.put("traffic calming request", 111);
		
		tabHash.put("report blocked crosswalk", 112);
		
		tabHash.put("report missing crosswalk", 113);
		
		tabHash.put("report stop sign running", 114);
		
		tabHash.put("request stop for pedestrian in crosswalk sign", 115);
		
		tabHash.put("covered fire hydrant", 116);
		
		tabHash.put("covered storm drain", 117);
		
		tabHash.put("report business plowing snow onto sidewalk", 118);
		
		tabHash.put("snow on crosswalk", 119);
		
		tabHash.put("unplowed street", 120);
		
		tabHash.put("unshoveled sidewalk", 121);
		
		tabHash.put("report failure to clean sidewalk", 122);
		
		tabHash.put("request street cleaning", 123);
		
		tabHash.put("unshoveled sidewalk", 124);
		
		tabHash.put("limousine complaint", 125);
		
		tabHash.put("property lost in taxi", 126);
		
		tabHash.put("taxi complaint", 127);
		
		tabHash.put("taxi office complaint", 128);
		tabHash.put("taxi inspectors complaint", 128);
		
		tabHash.put("clogged catch basin", 129);
		
		tabHash.put("request cleaning of sewage from street", 130);
		tabHash.put("request cleaning of sewage from sidewalk", 130);
		
		tabHash.put("no heat", 131);
		tabHash.put("insufficient heat", 131);
		
		tabHash.put("no hot water", 132);
		tabHash.put("insufficient hot water", 132);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hoboken311, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item){
		super.onOptionsItemSelected(item);
    	Intent intent;
    	
    	System.out.println("VOICE COMMAND MENU SELECTED!!!!!");
    	
    	switch(item.getItemId()){  //VOICE RECOGNITION
    	case(R.id.menu_voice_311):
    		System.out.println("inside meun_voice case !!!!");
    		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
    		
    		try{
    			startActivityForResult(intent, VOICE_REQUEST);
    			
    		} catch(ActivityNotFoundException a){
    			Toast t = Toast.makeText(getApplicationContext(),
    					"Your device does not support SpeechToText", 
    					Toast.LENGTH_SHORT);
    			t.show();
    		}
    		
    		return true;
    	}
    	
    	return false;
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	Toast t = Toast.makeText(getApplicationContext(), "Improper Voice Command", Toast.LENGTH_SHORT);
    	
    	switch(requestCode){ //INPUT VOICE INTO STRING
    	case VOICE_REQUEST:
    		if (resultCode == RESULT_OK && null !=data){
    			text = data.getStringArrayListExtra(
    					RecognizerIntent.EXTRA_RESULTS);
    			if(tabHash.containsKey(text.get(0))){
    					System.out.println("It Works!!!!!!!!");
    			//		Intent i = new Intent(context, MyCity311Map.class);
    			//		i.putExtra("REQUEST", text.get(0));
    				//	startActivity(i);
    					finish();
    			}
    					
    			}else{
    				t.show();
    			}
    			
    			System.out.println("this is the text " + text.get(0) );
    		}
    		
    		
    }
    

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		ArrayAdapter<CharSequence> adapter;
		String item = parent.getItemAtPosition(pos).toString();
	
		if(item.equals("Animals")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_animal, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Ask Us/ General FAQs")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_ask, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Business & Construction")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_bus, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Construction Issues")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_bus_const, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Excessive Signage")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Sidewalk Cafe Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		
		else if(item.equals("Garbage, Recycling & Graffiti")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_garb, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Garbage Related")){
			spn_prob_spec2.setVisibility(View.VISIBLE);

			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_garb_rel, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Graffiti Issues")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_garb_graff, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Illegal Posting of Flyers")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		
		else if(item.equals("Recycling Related")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_garb_recycle, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Request Container")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Request Public Bin Installation")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Health & Social Services")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_health, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Asbestos")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Bed Bugs")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Grease")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Hoarding")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Mold")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Odor Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Restaurant Operating Without License")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Suspected Food Poisoning")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Unlicensed Vendor")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Unsanitary Apartment")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Unsanitary Conditions")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		
		else if(item.equals("Housing & Private Property")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_housing, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Bells/Intercom Not Working")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Emergency Lighting")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Exterior Lighting")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Front Door Does Not Close")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Illegal Apartment/Property")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Illegal Sign")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Landlord Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Obstructed Sidewalk")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Occupancy Without Valid CO/CCO")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Smoke & Carbon Monoxide Detector")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}

		else if(item.equals("Uncut Grass")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Unsafe Structures")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Water Leaks")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Windows Need Replacing")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}

		
		else if(item.equals("Local Access Channel (Ch. 78 Cablevision/Ch. 47 FiOS)")){
			
			spn_prob_spec.setVisibility(View.GONE); 
			spn_prob_spec2.setVisibility(View.GONE);
			
			currentAdapter = null;
			currentAdapter2 = null;
			
			request = item;
			
		}
		
		else if(item.equals("Noise")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_noise, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Animal Noise")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("HVAC/Stationary Equipment Noise")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Parking")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_parking, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Illegally Parked Cars")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Parking Lot/Garage Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Parking Meter Malfunction")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Report Customer Service Experience")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Report Handicap Parking Abuse")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Report Parking Enforcement Experience")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Report Selective Parking Enforcement")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Parks & Trees")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_pt, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Parks")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_pt_park, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Tree Issues")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_pt_tree, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Signs, Signals & Lights")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_sign, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Transportation, Sidewalks & Streets")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("Bikes")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_bike, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Corner Car Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("HOP/Senior Shuttle Complaint")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Maintenance & Repairs")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_repair, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Pedestrian Safety")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_safety, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Snow")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_snow, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Street Cleaning")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_clean, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Taxi")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_trans_taxi, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Flooding")){
			spn_prob_spec2.setVisibility(View.VISIBLE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_flood, android.R.layout.simple_spinner_item);
			currentAdapter2 = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec2.setAdapter(adapter);
		}
		
		else if(item.equals("Utilities & Flooding")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
			adapter = ArrayAdapter.createFromResource(context, R.array.spn_spec_util, android.R.layout.simple_spinner_item);
			currentAdapter = adapter;
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spn_prob_spec.setAdapter(adapter);
		}
		
		else if(item.equals("No/Inefficient Heat")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("No/Inefficient Hot Water")){
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = item;	
		}
		
		else if(item.equals("Select Problem Specification")){
			spn_prob_spec.setVisibility(View.VISIBLE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
		}
		
		else if(item.equals("Select Problem")){
			spn_prob_spec.setVisibility(View.GONE);
			spn_prob_spec2.setVisibility(View.GONE);
			
			request = null;
			
		}else{
			//spn_prob_spec2.setVisibility(View.GONE);
			request = item;
		}
		
		
		
		System.out.println("THIS IS THE REQUEST!!!!! REQUEST =  " + request);
		
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}


}
