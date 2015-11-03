package kn.tcg.tests;

import kn.tcg.controller.engine.Engine;
import kn.tcg.model.gamedata.stages.GameStage;
import org.junit.*;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.*;
import java.util.logging.Logger;

public class Tests {
    Engine engine = new Engine();
    Logger logger = Logger.getLogger(Tests.class.getName());

    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            logger.severe("Test "+description.getDisplayName()+" failed! "+e.getMessage());
            super.failed(e, description);
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            logger.warning("Test " + description.getDisplayName() + " was skipped! " + e.getMessage());
            super.skipped(e, description);
        }

        @Override
        protected void succeeded(Description description) {
            logger.info("Test " + description.getDisplayName() + " succeeded! ");
            super.succeeded(description);
        }

        @Override
        protected void starting(Description description) {
            logger.info("----------------------\nStarting "+description.getDisplayName()+" test");
            super.starting(description);
        }

        @Override
        protected void finished(Description description) {
            logger.info("Finishing "+description.getDisplayName()+" test\n----------------------\n");
            super.finished(description);
        }
    };

    @Rule
    public RuleChain chain = RuleChain
            .outerRule(testWatcher);

    @Before
    public void initGame(){
        engine.initGame();
    }

    @Test
    public void verifyDataNotNull() throws IOException{
        Assert.assertEquals(engine.getGameData().getGameStage(), GameStage.STANDBY);
        Assert.assertNotNull(engine.getGameData().getActivePlayer());
        Assert.assertNotNull(engine.getGameData().getInactivePlayer());
    }

    @Test
    public void performCommandsTest() throws IOException{
        engine.performCommand("attack");
        Assert.assertEquals(engine.getGameData().getGameStage(), GameStage.ATTACK);

        engine.performCommand("exit");
        Assert.assertEquals(engine.getGameData().getGameStage(), GameStage.STANDBY);

        engine.performCommand("heal");
        Assert.assertEquals(engine.getGameData().getGameStage(), GameStage.HEAL);

        engine.performCommand("exit");
        Assert.assertEquals(engine.getGameData().getGameStage(), GameStage.STANDBY);
    }

    @Test
    public void verifyRoundNumberChanges(){
        for (int i = 0; i <= 3; i++){
            engine.performCommand("1");
        }

        Assert.assertEquals(engine.getGameData().getRoundNumber(), 5);
    }

    @Test
    public void verifyDeckSizeDecreases(){
        for (int i = 0; i <= 3; i++){
            engine.performCommand("1");
        }

        Assert.assertEquals(engine.getGameData().getActivePlayer().cardset.getDeck().size(), 13);
    }

    @Test
     public void verifyHealthIsDecreased(){
        for (int i = 0; i <= 21; i++){
            engine.performCommand("1");
        }
        Assert.assertEquals(engine.getGameData().getActivePlayer().getHealth(), 20);
    }

    @Test
     public void verifyHealthIsRestoredAndManaReduced(){
        for (int i = 0; i <= 21; i++){
            engine.performCommand("1");
        }
        Assert.assertEquals(engine.getGameData().getActivePlayer().getHealth(), 20);

        int card = (Integer) engine.getGameData().getActivePlayer().cardset.getHand().get(1);
        int mana = engine.getGameData().getActivePlayer().getManaCount();

        engine.performCommand("3");
        engine.performCommand("1");

        Assert.assertEquals(engine.getGameData().getActivePlayer().getHealth(), 20 + card);
        Assert.assertEquals(engine.getGameData().getActivePlayer().getManaCount(), mana-card);
    }

    @Test
     public void verifyHealthIsDecreasedAndManaReducedOnAttack(){
        for (int i = 0; i <= 21; i++){
            engine.performCommand("1");
        }
        Assert.assertEquals(engine.getGameData().getActivePlayer().getHealth(), 20);

        int card = (Integer) engine.getGameData().getActivePlayer().cardset.getHand().get(1);
        int mana = engine.getGameData().getActivePlayer().getManaCount();

        engine.performCommand("2");
        engine.performCommand("1");

        Assert.assertEquals(engine.getGameData().getInactivePlayer().getHealth(), 20 - card);
        Assert.assertEquals(engine.getGameData().getActivePlayer().getManaCount(), mana-card);
    }

    @Test
    public void verifyManaCountDoesNotExceedMaxMana(){
        for (int i = 0; i <= 25; i++){
            engine.performCommand("1");
        }

        Assert.assertEquals(engine.getGameData().getActivePlayer().getManaCount(), 10);
    }

    @Test
    public void verifyGameEndsWhenPlayerHealthZero(){
        for (int i = 0; i <= 60; i++){
            engine.performCommand("1");
        }
        Assert.assertTrue(engine.performCommand("1").contains("winner"));
    }

    @Test
    public void verifyGameEndsWhenPlayerHealthZeroAfterAttack(){
        for (int i = 0; i <= 59; i++){
            engine.performCommand("1");
        }
        engine.performCommand("2");
        Assert.assertTrue(engine.performCommand("2").contains("winner"));
    }
}
