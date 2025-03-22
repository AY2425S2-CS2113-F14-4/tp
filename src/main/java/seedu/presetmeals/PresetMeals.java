package seedu.presetmeals;


public class PresetMeals {
    static String[] allPresetMeals;

    public PresetMeals() {
        addAllPresetMeals();
    }

    public String[] getPresetMeals() {
        return allPresetMeals;
    }

    private static void addAllPresetMeals() {
        String allPresetMealsString =
                "Bak Chang | Glutinous Rice (1.50) | Pork (1.50) | Mushrooms (0.70) | Salt (0.20)\n" +
                "Bak Chor Mee | Noodles (1.00) | Minced Pork (1.50) | Vinegar (0.30) | Pork Fat (0.80)\n" +
                "Bak Kut Teh | Pork Ribs (2.50) | Herbs (0.80) | Garlic (0.30) | Soy Sauce (0.50)\n" +
                "Beef Rendang | Beef (3.50) | Coconut Milk (1.00) | Spices (0.70) | Herbs (0.50)\n" +
                "Beef Satay | Beef (3.00) | Peanut Sauce (0.70) | Onion (0.50) | Rice Cakes (0.50)\n" +
                "Black Bean Spare Ribs | Pork Ribs (3.00) | Black Bean (0.80) | Garlic (0.30) | " +
                "Soy Sauce (0.50)\n" +
                "Black Pepper Beef | Beef (3.00) | Black Pepper (0.50) | Onions (0.60) | Garlic (0.30)\n" +
                "Black Pepper Squid | Squid (3.00) | Black Pepper (0.50) | Onion (0.50) | Garlic (0.30)\n" +
                "Braised Abalone Rice | Abalone (4.50) | Rice (0.80) | Mushroom (0.70) | Garlic (0.30)\n" +
                "Braised Duck Rice | Duck (3.00) | Rice (0.80) | Soy Sauce (0.50) | Vegetables (0.70)\n" +
                "Braised Eggplant | Eggplant (1.50) | Soy Sauce (0.50) | Garlic (0.30) | Chilli (0.50)\n" +
                "Braised Tofu | Tofu (1.00) | Mushrooms (0.70) | Soy Sauce (0.50) | Garlic (0.30)\n" +
                "Carrot Cake | Rice Flour Cake (1.20) | Preserved Radish (0.50) | Chilli (0.30) | " +
                "Bean Sprouts (0.40)\n" +
                "Char Kway Teow | Flat Rice Noodles (1.50) | Prawns (2.50) | Chinese Sausage (1.00) | " +
                "Bean Sprouts (0.40)\n" +
                "Char Siew Rice | Char Siew (2.50) | Rice (0.80) | Barbecue Sauce (0.70)\n" +
                "Chicken Rice | Chicken (2.50) | Rice (0.80) | Sambal Chilli (0.50)\n" +
                "Chicken Satay | Chicken (2.00) | Peanut Sauce (0.70) | Cucumber (0.40) | Rice Cakes (0.50)\n" +
                "Chili Crab | Crab (4.00) | Chilli (0.50) | Tomato Sauce (1.00) | Egg (0.80)\n" +
                "Chilli Prawns | Prawns (2.50) | Chilli (0.50) | Garlic (0.30) | Lemon (0.30)\n" +
                "Claypot Chicken Rice | Chicken (2.00) | Rice (0.80) | Mushroom (0.70) | Chinese Sausage (1.00)\n" +
                "Claypot Rice | Rice (0.80) | Chicken (2.00) | Chinese Sausage (1.00) | Mushroom (0.70)\n" +
                "Crispy Fried Chicken Wings | Chicken Wings (2.00) | Flour (0.50) | Spices (0.40) | Oil (0.70)\n" +
                "Curry Beef Brisket | Beef Brisket (3.50) | Curry (1.00) | Potato (0.50) | Vegetables (0.70)\n" +
                "Curry Chicken Rice | Chicken (2.00) | Curry (1.00) | Rice (0.80) | Vegetables (0.70)\n" +
                "Curry Laksa | Rice Noodles (1.20) | Chicken (2.00) | Laksa Broth (2.00) | Bean Sprouts (0.40)\n" +
                "Curry Puff | Curry (0.70) | Potato (0.50) | Chicken (1.50) | Pastry (0.80)\n" +
                "Deep Fried Squid | Squid (3.00) | Flour (0.50) | Chilli (0.50) | Oil (0.70)\n" +
                "Egg Fried Rice | Rice (0.80) | Egg (0.80) | Spring Onion (0.40) | Soy Sauce (0.50)\n" +
                "Fish Head Curry | Fish Head (3.00) | Curry (1.00) | Vegetables (0.80) | Tamarind (0.30)\n" +
                "Fish Soup | Fish (3.00) | Vegetables (0.80) | Noodles (1.00) | Herbs (0.50)\n" +
                "Fried Bee Hoon | Rice Vermicelli (1.00) | Seafood (2.00) | Egg (0.80) | Garlic (0.20)\n" +
                "Fried Carrot Cake | Rice Cake (1.20) | Egg (0.80) | Chilli Sauce (0.50) | Bean Sprouts (0.40)\n" +
                "Fried Clams | Clams (2.50) | Garlic (0.30) | Butter (0.80) | Parsley (0.40)\n" +
                "Fried Fish Ball Noodles | Fish Balls (1.50) | Noodles (1.00) | Broth (1.00) | Vegetables (0.70)\n" +
                "Fried Fish Cake | Fish Cake (1.50) | Batter (0.70) | Chilli (0.30) | Vegetables (0.70)\n" +
                "Fried Oyster Cake | Oysters (2.00) | Batter (0.70) | Egg (0.80) | Chilli (0.30)\n" +
                "Fried Rice Noodles | Rice Noodles (1.00) | Chicken (2.00) | Soy Sauce (0.50) | Vegetables (0.70)\n" +
                "Fried Spring Rolls | Spring Rolls (1.00) | Vegetables (0.80) | Pork (1.50) | Dipping Sauce (0.50)\n" +
                "Garlic Butter Crab | Crab (4.00) | Garlic (0.30) | Butter (0.80) | Herbs (0.50)\n" +
                "Garlic Prawns | Prawns (2.50) | Garlic (0.30) | Butter (0.80) | Parsley (0.40)\n" +
                "Hainanese Curry Rice | Rice (0.80) | Curry Chicken (2.00) | Vegetables (0.70) | Soup (0.60)\n" +
                "Hainanese Pork Chop | Pork Chop (3.00) | Rice (0.80) | Garlic (0.30) | Vegetables (0.70)\n" +
                "Hokkien Mee | Yellow Noodles (1.20) | Prawns (2.50) | Pork Lard (1.00) | Fried Shallots (0.60)\n" +
                "Hokkien Prawn Mee | Noodles (1.00) | Prawns (2.50) | Pork Lard (1.00) | Fried Shallots (0.60)\n" +
                "Honey Chicken | Chicken (2.00) | Honey (0.80) | Sesame Seeds (0.50) | Soy Sauce (0.50)\n" +
                "Honey Glazed Chicken | Chicken (2.00) | Honey (0.80) | Sesame (0.50) | Glaze (0.70)\n" +
                "Kaya Toast | Toast (0.50) | Kaya (0.70) | Butter (0.50)\n" +
                "Kung Pao Chicken | Chicken (2.00) | Peanuts (0.70) | Chilli (0.50) | Sichuan Pepper (0.40)\n" +
                "Kway Chap | Braised Pork (2.00) | Rice Noodles (1.00) | Soy Sauce (0.50) | Hard-Boiled Egg (0.80)\n" +
                "Laksa | Rice Noodles (1.20) | Prawns (3.00) | Laksa Broth (2.00)\n" +
                "Lamb Satay | Lamb (3.50) | Peanut Sauce (0.70) | Onions (0.50) | Rice Cakes (0.50)\n" +
                "Lemon Fish | Fish (3.00) | Lemon (0.50) | Herbs (0.50) | Olive Oil (0.70)\n" +
                "Lor Mee | Noodles (1.00) | Thick Gravy (1.50) | Egg (0.80) | Minced Meat (1.00)\n" +
                "Mee Rebus | Yellow Noodles (1.00) | Prawn Gravy (1.50) | Tofu (0.70) | Boiled Egg (0.80)\n" +
                "Mee Siam | Vermicelli (1.00) | Shrimp Paste (0.70) | Lime (0.30) | Bean Sprouts (0.40)\n" +
                "Mixed Seafood Noodles | Noodles (1.00) | Mixed Seafood (3.00) | Garlic (0.30) | " +
                "Bean Sprouts (0.40)\n" +
                "Mutton Satay | Mutton (3.50) | Peanut Sauce (0.70) | Cucumber (0.40) | Rice Cakes (0.50)\n" +
                "Nasi Goreng | Rice (0.80) | Egg (0.80) | Chili Paste (0.50) | Prawns (2.50)\n" +
                "Nasi Lemak | Rice (0.80) | Coconut Milk (0.60) | Sambal (0.70) | Anchovies (0.50) | Egg (0.80) | " +
                "Peanuts (0.30)\n" +
                "Oyster Bee Hoon | Rice Vermicelli (1.00) | Oysters (2.00) | Garlic (0.30) | Bean Sprouts (0.40)\n" +
                "Oyster Omelette | Oysters (2.00) | Egg (0.80) | Batter (0.50) | Chilli (0.30)\n" +
                "Pan-fried Dumplings | Dumplings (1.50) | Pork (1.50) | Cabbage (0.50) | Soy Sauce (0.50)\n" +
                "Penang Laksa | Rice Noodles (1.20) | Mackerel (3.00) | Tamarind (0.50) | Herbs (0.70)\n" +
                "Popiah | Soft Wrapper (0.50) | Vegetables (0.80) | Peanut Sauce (0.70) | Tofu (0.50)\n" +
                "Popiah Spring Roll | Popiah Skin (0.50) | Vegetables (0.80) | Sauce (0.70) | Tofu (0.50)\n" +
                "Pork Siu Mai | Pork (1.50) | Shrimp (2.00) | Mushrooms (0.70) | Soy Sauce (0.50)\n" +
                "Prawn Mee | Yellow Noodles (1.00) | Prawns (2.50) | Broth (1.00) | Vegetables (0.70)\n" +
                "Prawn Sambal | Prawns (2.50) | Sambal (0.80) | Lime (0.30) | Garlic (0.30)\n" +
                "Roasted Duck | Duck (3.50) | Spices (0.70) | Honey (0.80) | Soy Sauce (0.50)\n" +
                "Roasted Pork Belly | Pork Belly (3.00) | Rice (0.80) | Soy Sauce (0.50) | Garlic (0.30)\n" +
                "Rojak | Mixed Fruits (1.00) | Peanut Sauce (0.80) | Cucumber (0.30) | Tofu (0.50)\n" +
                "Roti Prata | Flatbread (0.80) | Curry (0.70) | Dipping Sauce (0.40)\n" +
                "Salted Egg Squid | Squid (3.00) | Salted Egg (1.00) | Chilli (0.50) | Oil (0.70)\n" +
                "Salted Egg Yolk Chicken | Chicken (2.50) | Salted Egg Yolk (0.80) | Spices (0.50)\n" +
                "Salted Fish Fried Rice | Rice (0.80) | Salted Fish (1.50) | Egg (0.80) | Vegetables (0.70)\n" +
                "Sambal Egg | Eggs (1.00) | Sambal (0.80) | Tomatoes (0.70) | Spring Onion (0.40)\n" +
                "Sambal Kang Kong | Water Spinach (1.00) | Sambal (0.80) | Tempeh (0.70)\n" +
                "Sambal Mackerel | Mackerel (3.00) | Sambal (0.80) | Vegetables (0.70)\n" +
                "Sambal Prawns | Prawns (2.50) | Sambal (0.80) | Lime (0.30) | Garlic (0.30)\n" +
                "Sambal Sotong | Squid (3.00) | Sambal (0.80) | Lime (0.30) | Garlic (0.30)\n" +
                "Sambal Stingray | Stingray (4.50) | Sambal (1.00) | Lime (0.30) | Banana Leaf (0.20)\n" +
                "Satay | Chicken (1.80) | Peanut Sauce (0.70) | Cucumber (0.40) | Rice Cakes (0.50)\n" +
                "Seafood Soup | Mixed Seafood (3.00) | Broth (1.50) | Ginger (0.30) | Vegetables (0.80)\n" +
                "Singapore Fried Hokkien Mee | Noodles (1.00) | Prawns (2.50) | Pork (1.50) | Bean Sprouts (0.40)\n" +
                "Sliced Fish Soup | Fish (3.00) | Vegetables (0.80) | Noodles (1.00) | Herbs (0.50)\n" +
                "Spicy Beef Noodles | Beef (3.00) | Noodles (1.00) | Chilli (0.50) | Broth (1.00)\n" +
                "Spicy Cuttlefish | Cuttlefish (3.00) | Chilli (0.50) | Garlic (0.30) | Lime (0.30)\n" +
                "Spicy Fried Tofu | Tofu (1.00) | Chilli (0.50) | Garlic (0.30) | Soy Sauce (0.50)\n" +
                "Spicy Noodle Soup | Noodles (1.00) | Beef (3.00) | Chilli (0.50) | Broth (1.00)\n" +
                "Steamboat | Seafood (3.00) | Broth (1.50) | Vegetables (1.00) | Tofu (0.70)\n" +
                "Steamed Dumplings | Dumplings (1.50) | Pork (1.50) | Ginger (0.30) | Soy Sauce (0.50)\n" +
                "Steamed Fish | Fish (3.00) | Ginger (0.50) | Soy Sauce (0.50) | Spring Onion (0.30)\n" +
                "Stir-fried Kangkong | Water Spinach (1.00) | Garlic (0.30) | Chilli (0.50) | Oyster Sauce (0.50)\n" +
                "Stir-fried Vegetables | Mixed Vegetables (1.00) | Garlic (0.30) | Oyster Sauce (0.50) | " +
                "Bean Sprouts (0.40)\n" +
                "Sweet and Sour Pork | Pork (2.50) | Pineapple (0.80) | Bell Pepper (0.70) | Vinegar (0.30)\n" +
                "Teochew Porridge | Rice Porridge (1.00) | Fish (2.50) | Pickles (0.50) | Ginger (0.30)\n" +
                "Teriyaki Chicken Rice | Chicken (2.00) | Teriyaki Sauce (0.80) | Rice (0.80) | Vegetables (0.70)\n" +
                "Vegetable Bee Hoon | Rice Vermicelli (1.00) | Vegetables (1.00) | Bean Sprouts (0.40) | " +
                "Fried Shallots (0.60)\n" +
                "Wanton Noodle | Egg Noodles (1.00) | Pork (1.50) | Wanton (0.80) | Broth (0.70)\n" +
                "Yong Tau Foo | Tofu (0.80) | Fish Balls (1.00) | Vegetables (0.70) | Rice Noodles (1.00)\n";
        allPresetMeals = allPresetMealsString.split("\n");
    }
}
