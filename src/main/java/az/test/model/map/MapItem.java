package az.test.model.map;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.Barbarian;
import az.test.model.army.other.BeastArmy;
import az.test.model.army.other.MartialArtist;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.ride.Rider;
import az.test.model.army.theif.Thief;

import java.io.Serializable;

public class MapItem implements Serializable {
	public int id;
	public boolean canStay = true;
	public BaseUnit army = null;
	public int y;
	public int x;
	public String name;

	public static MapItem generateById(int id, int y, int x) {
		MapItem mi;
		switch (id) {
		case 0:
			mi = new Plain();// all
			break;
		case 1:
			mi = new City();// all
			break;
		case 2:
			mi = new Grassland();// all
			break;
		case 3:
			mi = new Bridge();// all
			break;
		case 4:
			mi = new Forest();// all but Ride
			break;
		case 5:
			mi = new Wasteland();// all
			break;
		case 6:
			mi = new Mountain();// MA(武术家),Thief only
			break;
		case 7:
			mi = new Village();// all
			break;
		case 8:
			mi = new Abatis();// all
			break;
		case 9:
			mi = new Barrack();// all
			break;
		case 10:
			mi = new TreasureStore();// all
			break;
		case 11:
			mi = new Granary();// all
			break;
		case 12:
			mi = new River();// none
			break;
		case 13:
			mi = new Cliff();// none
			break;
		case 14:
			mi = new Wall();// none
			break;
		case 15:
			mi = new Gate();// none
			break;
		case 16:
			mi = new Fence();// none
			break;
		case 17:
			mi = new House();// none
			break;
		default:
			mi = new MapItem();
			break;
		}
		mi.y = y;
		mi.x = x;
		return mi;
	}

	public boolean isRestoreHPPlace() {
        return this instanceof Village || this instanceof Abatis || this instanceof Barrack;
    }

	public boolean isRestoreMoralePlace() {
        return this instanceof Village || this instanceof Abatis;
    }

	public int queryCost(BaseUnit army) {
		if (id < 4) {
			return 1;
		}
		if (id > 11) {
			return Integer.MAX_VALUE;
		}
		if (this instanceof Forest) {
			if (army instanceof Rider) {
				return Integer.MAX_VALUE;
			}
		}
		if (this instanceof Wasteland) {
			if (army instanceof MilitaryBand || army instanceof TransportTeam || army instanceof Rider) {
				return 2;
			}
			return 1;
		} else if (this instanceof Mountain) {
			if (army instanceof Thief || army instanceof MartialArtist || army instanceof Barbarian
					|| army instanceof BeastArmy) {
				return 1;
			}
			return Integer.MAX_VALUE;
		}
		if (this instanceof Village) {
			return 2;
		}
		if (this instanceof Abatis || this instanceof Barrack || this instanceof TreasureStore
				|| this instanceof Granary) {
			if (army instanceof Rider) {
				return 3;
			}
			return 2;
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public String toString() {
		return y + "," + x + "\n";
	}

}
