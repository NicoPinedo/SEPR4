package main.drtn.game.entity;

import main.drtn.game.GameEngine;
import main.drtn.game.Trade;
import main.drtn.game.enums.ResourceType;
import main.drtn.game.screens.GameScreen;
import main.drtn.game.screens.GameScreen;

import java.util.Random;

/**
 * @author Team Fractal
 *
 * New for Assessment 3
 */
public class AiPlayer extends Player {
    static private Random rnd = new Random();

    public AiPlayer(int i) {
        super(i);
    }

    @Override
    public boolean isAi() {
        return true;
    }

    /**
     * Called by GameEngine to get the AI player's action, according to the current phase in the game engine
     *
     * @param engine The game engine
     * @param screen The game screen
     */
    public void performPhase(GameEngine engine, GameScreen screen) {
        Market market = engine.market();

        Trade trade = engine.getCurrentPendingTrade();
        if (trade != null) {
            // trade.oreAmount * trade.energyAmount * trade.foodAmount;
            // Likelihood:
            int total = market.getOreBuyPrice() * trade.oreAmount
                    + market.getEnergyBuyPrice() * trade.energyAmount
                    + market.getFoodBuyPrice() * trade.foodAmount;

            double likelihood = calculateLikelihood(total, (double)trade.getPrice());

            if (rnd.nextDouble() < likelihood) {
                trade.execute();
                System.out.println("Accept offer.");
            } else {
                System.out.println("Reject offer.");
            }

            engine.closeTrade();
        }


        switch(engine.getPhase()) {
            // Claim land
            case 1:
                for (Tile tile : engine.tiles()) {
                    if (!tile.isOwned()) {
                        engine.selectTile(tile);
                        engine.claimTile();
                        break;
                    }
                }
                break;

            // Buy roboticon
            case 2:
                while (market.getRoboticonStock() > 1 && market.getRoboticonBuyPrice() < getResource(ResourceType.MONEY)) {
                    try {
                        System.out.println("AI: Bought a roboticon at price $" + market.getRoboticonBuyPrice());
                        market.buy(ResourceType.ROBOTICON, 1, this);
                    } catch (Exception e) {
                        System.out.println("Can't buy stuff.");
                        break;
                    }
                }
                engine.nextPhase();
                break;

            // Place roboticon
            case 3:
                for (Tile tile : getTileList()) {
                    if (getRoboticonInventory() == 0) {
                        break;
                    }

                    if (!tile.hasRoboticon()) {
                        engine.selectTile(tile);
                        engine.deployRoboticon();
                    }
                }
                engine.nextPhase();
                break;

            // produce resources.
            case 4:
                engine.nextPhase();
                break;

            // Market
            case 5:
                while (getResource(ResourceType.MONEY) < market.getRoboticonBuyPrice() + 20) {
                    try {
                        if (getResource(ResourceType.ORE) > 0) {
                            market.sell(ResourceType.ORE, 1, this);
                        }
                        if (getResource(ResourceType.ENERGY) > 0) {
                            market.sell(ResourceType.ENERGY, 1, this);
                        }
                        if (getResource(ResourceType.FOOD) > 0) {
                            market.sell(ResourceType.FOOD, 1, this);
                        }

                        if (getResource(ResourceType.ENERGY) + getResource(ResourceType.ORE) + getResource(ResourceType.FOOD) == 0)
                            break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                engine.nextPhase();
                break;
        }
    }

    /**
     * Calculates a value for the likelihood of the AI accepting a trade. Used in performPhase.
     *
     * @param marketPrice The current price of a certain resource at the market
     * @param offerPrice The proce another player has offered for the resource
     * @return the likelyhood
     */
    public double calculateLikelihood(double marketPrice, double offerPrice){
        if (offerPrice > marketPrice * 1.5) return 0;
        if (marketPrice > offerPrice * 1.5) return 1;

        return marketPrice / (offerPrice * 1.5);
    }
}
