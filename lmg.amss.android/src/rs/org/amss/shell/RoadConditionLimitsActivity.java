package rs.org.amss.shell;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import rs.org.amss.R;

import java.util.Locale;

public class RoadConditionLimitsActivity extends BaseActivity {

	public Context context = this;
	private Integer[] statesIDs = { R.string.Albanija, R.string.Austrija,
			R.string.Belgija, R.string.Bugarska, R.string.BosnaiHercegovina,
			R.string.Ceska, R.string.CrnaGora, R.string.Finska,
			R.string.Francuska, R.string.Grcka, R.string.Holandija,
			R.string.Hrvatska, R.string.Irska, R.string.Island,
			R.string.Italija, R.string.Kipar, R.string.Luksemburg,
			R.string.Madjarska, R.string.Makedonija, R.string.Nemacka,
			R.string.Norveska, R.string.Portugalija, R.string.Rumunija,
			R.string.Rusija, R.string.Slovacka, R.string.Slovenija,
			R.string.Svajcarska, R.string.Turska, R.string.Ukrajina,
			R.string.VelikaBritanija, R.string.Danska, R.string.Spanija,
			R.string.Poljska, R.string.Svedska, R.string.Srbija };

	private Integer[] detailsIDs = { R.string.Albanija_percents,
			R.string.Austrija_percents, R.string.Belgija_percents,
			R.string.Bugarska_percents, R.string.BosnaiHercegovina_percents,
			R.string.Ceska_percents, R.string.CrnaGora_percents,
			R.string.Finska_percents, R.string.Francuska_percents,
			R.string.Grcka_percents, R.string.Holandija_percents,
			R.string.Hrvatska_percents, R.string.Irska_percents,
			R.string.Island_percents, R.string.Italija_percents,
			R.string.Kipar_percents, R.string.Luksemburg_percents,
			R.string.Madjarska_percents, R.string.Makedonija_percents,
			R.string.Nemacka_percents, R.string.Norveska_percents,
			R.string.Portugalija_percents, R.string.Rumunija_percents,
			R.string.Rusija_percents, R.string.Slovacka_percents,
			R.string.Slovenija_percents, R.string.Svajcarska_percents,
			R.string.Turska_percents, R.string.Ukrajina_percents,
			R.string.VelikaBritanija_percents, R.string.Danska_percents,
			R.string.Spanija_percents, R.string.Poljska_percents,
			R.string.Svedska_percents, R.string.Srbija_percents };
	private Integer[] stateImagesIDs = { R.drawable.state_al,
			R.drawable.state_a, R.drawable.state_b, R.drawable.state_bg,
			R.drawable.state_bih, R.drawable.state_cz, R.drawable.state_mne,
			R.drawable.state_fin, R.drawable.state_f, R.drawable.state_gr,
			R.drawable.state_nl, R.drawable.state_hr, R.drawable.state_irl,
			R.drawable.state_is, R.drawable.state_i, R.drawable.state_cy,
			R.drawable.state_l, R.drawable.state_h, R.drawable.state_mk,
			R.drawable.state_d, R.drawable.state_n, R.drawable.state_p,
			R.drawable.state_ro, R.drawable.state_rus, R.drawable.state_sk,
			R.drawable.state_slo, R.drawable.state_ch, R.drawable.state_tr,
			R.drawable.state_ua, R.drawable.state_gb, R.drawable.state_dk,
			R.drawable.state_e, R.drawable.state_pl, R.drawable.state_s,
			R.drawable.state_srb, };
	private Integer[] limitSignOneImagesIDs = { R.drawable.limit_40,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_60, R.drawable.limit_50, R.drawable.limit_60,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_60, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_50,
			R.drawable.limit_60, R.drawable.limit_48, R.drawable.limit_50,
			R.drawable.limit_50, R.drawable.limit_50, R.drawable.limit_30_60,
			R.drawable.limit_50, };
	private Integer[] limitSignTwoImagesIDs = { R.drawable.limit_80,
			R.drawable.limit_100, R.drawable.limit_90, R.drawable.limit_90,
			R.drawable.limit_80, R.drawable.limit_90, R.drawable.limit_80,
			R.drawable.limit_80, R.drawable.limit_90, R.drawable.limit_90,
			R.drawable.limit_80, R.drawable.limit_90, R.drawable.limit_80,
			R.drawable.limit_90, R.drawable.limit_90, R.drawable.limit_80,
			R.drawable.limit_90, R.drawable.limit_90, R.drawable.limit_80,
			R.drawable.limit_100, R.drawable.limit_80, R.drawable.limit_90,
			R.drawable.limit_90, R.drawable.limit_90, R.drawable.limit_90,
			R.drawable.limit_90, R.drawable.limit_80, R.drawable.limit_90,
			R.drawable.limit_90, R.drawable.limit_96, R.drawable.limit_80,
			R.drawable.limit_90, R.drawable.limit_90, R.drawable.limit_70_100,
			R.drawable.limit_80 };
	private Integer[] limitSignThreeImagesIDs = { null, R.drawable.limit_130,
			R.drawable.limit_120, R.drawable.limit_130, R.drawable.limit_130,
			R.drawable.limit_130, R.drawable.limit_120, R.drawable.limit_120,
			R.drawable.limit_130, R.drawable.limit_130, R.drawable.limit_120,
			R.drawable.limit_130, R.drawable.limit_120, null,
			R.drawable.limit_130, R.drawable.limit_100, R.drawable.limit_130,
			R.drawable.limit_130, R.drawable.limit_120, R.drawable.limit_130,
			R.drawable.limit_100, R.drawable.limit_120, R.drawable.limit_130,
			R.drawable.limit_110, R.drawable.limit_130, R.drawable.limit_130,
			R.drawable.limit_120, R.drawable.limit_120, R.drawable.limit_130,
			R.drawable.limit_112, R.drawable.limit_110_130,
			R.drawable.limit_120, R.drawable.limit_130, R.drawable.limit_110,
			R.drawable.limit_120 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.limitations);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		setHomeAction(R.drawable.ic_roads, R.string.roads_bounders_text, InfoActivity.class);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.roads_bounders_text).toUpperCase(Locale.GERMANY));

		checkIsLogIn(RoadConditionLimitsActivity.this);

		TableLayout table = (TableLayout) findViewById(R.id.limit_table);

		for (int i = 0; i < statesIDs.length; i++) {
			TableRow row = (TableRow) LayoutInflater.from(RoadConditionLimitsActivity.this).inflate(R.layout.limit_item, null);
			((TextView) row.findViewById(R.id.state_label)).setBackgroundResource(stateImagesIDs[i]);
			((TextView) row.findViewById(R.id.state)).setText(statesIDs[i]);
			((TextView) row.findViewById(R.id.limit_sign_one)).setBackgroundResource(limitSignOneImagesIDs[i]);
			((TextView) row.findViewById(R.id.limit_sign_two)).setBackgroundResource(limitSignTwoImagesIDs[i]);
			if (limitSignThreeImagesIDs[i] != null)
				((TextView) row.findViewById(R.id.limit_sign_three)).setBackgroundResource(limitSignThreeImagesIDs[i]);
			else
				((TextView) row.findViewById(R.id.limit_sign_three)).setVisibility(0);
			((TextView) row.findViewById(R.id.details)).setText(detailsIDs[i]);
			row.setBackgroundResource(R.drawable.table_border);
			table.addView(row);
		}
	}
}
