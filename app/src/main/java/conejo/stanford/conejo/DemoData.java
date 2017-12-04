package conejo.stanford.conejo;
/*Data for operating the demo, currently hardcoded, could be passed to a server for actual app*/
public class DemoData {

    //------------------------------------For Wardrobe Screen---------------------------------------

    public static int[] shirts = {R.mipmap.shirt_1,R.mipmap.shirt_2,R.mipmap.shirt_3,R.mipmap.shirt_4,
            R.mipmap.shirt_5,R.mipmap.shirt_6,R.mipmap.shirt_7};

    public static int[] pants = {R.mipmap.pants_1,R.mipmap.pants_2,R.mipmap.pants_3,R.mipmap.pants_4,
    R.mipmap.pants_5, R.mipmap.pants_6};

    public static int[] shoes = {R.mipmap.shoe_1,R.mipmap.shoe_2,R.mipmap.shoe_3, R.mipmap.shoe_4,
    R.mipmap.shoe_5,R.mipmap.shoe_6};

    public static int[] accesories = {R.mipmap.accesory_1,R.mipmap.accesory_2,R.mipmap.accesory_3};

    //----------------------------For Recommended Outfits Screen------------------------------------

    //For Description
    public static String[] shirtPantBrands = {"GAP", "Ralph Lauren", "Diesel", "Aeropostale","American Eagle", "Lacoste"};
    public static String[] shoeBrands = {"Converse", "Nike", "Adidas", "New Balance","Puma", "Vans"};
    public static String[] holidaySeason = {"Summer", "Winter", "Autumn", "Spring"};
    public static String[] shirtPantSizes = {"XS", "S", "M", "L", "XL", "XXL"};
    public static String[] shoeSizes = {"9.0", "9.5", "10", "10.5", "11"};

    public static int randYear(){
        return (int)(Math.random()*15 + 1999);
    }

    public static double randPrice(){
        return ((int)(Math.random()*55 + 25)) + 0.99;
    }

    public static String randItem(String items[]){
        return items[(int)(Math.random() *items.length + 1)];
    }

    //Recommended items:
    public static int[] recommended_shirts = {};

    public static int[] recommended_pants = {};

    public static int[] recommended_shoes = {};

}
