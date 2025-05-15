package models.farming;

import models.RandomGenerator;
import models.time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum MixedSeedCrop {
        SUMMER_SEEDS(new ArrayList<>(List.of(
                CropSeeds.CORN_SEED,
                CropSeeds.PEPPER_SEED,
                CropSeeds.RADISH_SEED,
                CropSeeds.WHEAT_SEED,
                CropSeeds.POPPY_SEED,
                CropSeeds.SUNFLOWER_SEED,
                CropSeeds.SPANGLE_SEED
        ))),
        SPRING_SEEDS(new ArrayList<>(List.of(
                CropSeeds.CAULIFLOWER_SEED,
                CropSeeds.PARSNIP_SEED,
                CropSeeds.POTATO_SEED,
                CropSeeds.JAZZ_SEED,
                CropSeeds.TULIP_BULB
        ))),
        AUTUMN_SEEDS(new ArrayList<>(List.of(
                CropSeeds.CORN_SEED,
                CropSeeds.SUNFLOWER_SEED,
                CropSeeds.ARTICHOKE_SEED,
                CropSeeds.EGGPLANT_SEED,
                CropSeeds.PUMPKIN_STARTER,
                CropSeeds.FAIRY_SEED
        ))),
        WINTER_SEEDS(new ArrayList<>(List.of(CropSeeds.POWDER_MELON_SEED)));

        public final ArrayList<CropSeeds> possibleSeeds;
        MixedSeedCrop(ArrayList<CropSeeds> possibleSeeds) {
            this.possibleSeeds = possibleSeeds;
        }

        public ArrayList<CropSeeds> getPossibleSeeds() {
                return possibleSeeds;
        }

        public static CropSeeds getRandomSeed(Season season) {
                ArrayList<CropSeeds> seeds = new ArrayList<>();
                if(season == Season.SPRING){
                        seeds = SPRING_SEEDS.possibleSeeds;
                }
                else if(season == Season.AUTUMN){
                        seeds = AUTUMN_SEEDS.possibleSeeds;
                }
                else if(season == Season.WINTER){
                        seeds = WINTER_SEEDS.possibleSeeds;
                }
                else if(season == Season.SUMMER){
                        seeds = SUMMER_SEEDS.possibleSeeds;
                }
                else{
                        throw new IllegalArgumentException("Unsupported season: " + season);
                }
                return seeds.get(RandomGenerator.getInstance().randomInt(0,seeds.size()-1));
        }


}
