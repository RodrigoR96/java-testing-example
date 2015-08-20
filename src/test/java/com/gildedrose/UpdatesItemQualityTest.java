package com.gildedrose;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class UpdatesItemQualityTest {

  @Test
  public void qualityDegradesByOneWhenSellInIsPositive() {
    Item item = new Item("Thing", 1, 2);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(1));
  }

  @Test
  public void qualityDegradesByTwoOnceSellInIsZero() {
    Item item = new Item("Thing", 0, 3);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(1));
  }

  @Test
  public void qualityDegradesByTwoWhenSellInIsNegative() {
    Item item = new Item("Thing", -56, 3);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(1));
  }

  @Test
  public void qualityDoesNotDegradeBelowZero() {
    Item item = new Item("Thing", 5, 0);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(0));
  }

  @Test
  public void qualityDoesNotDegradeBelowZeroWhenSellInIsNegative() {
    Item item = new Item("Thing", -5, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(0));
  }

  @Test
  public void qualityDoesNotIncreaseAbove50() {
    Item item = new Item("Aged Brie", 5, 50);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(50));
  }

  @Test
  public void qualityDoesNotIncreaseAbove50WhenSellInIsNegative() {
    Item item = new Item("Aged Brie", -5, 49);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(50));
  }

  @Test
  public void qualityOfAgedBrieIncreasesBy1WhenSellInIsPositive() {
    Item item = new Item("Aged Brie", 5, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(2));
  }

  @Test
  public void qualityOfAgedBrieIncreasesBy2WhenSellInIsNegative() {
    Item item = new Item("Aged Brie", -5, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(3));
  }

  @Test
  public void sellInDecreasesBy1() {
    Item item = new Item("Thing", 10, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.sellIn, is(9));
  }

  @Test
  public void sellInDecreasesBy1EvenWhenNegative() {
    Item item = new Item("Thing", -10, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.sellIn, is(-11));
  }

  @Test
  public void sulfurasNeverChanges() {
    Item item = new Item("Sulfuras, Hand of Ragnaros", 8, 5);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(5));
    assertThat(item.sellIn, is(8));
  }

  @Test
  public void backstagePassesIncreaseInValueStrangely() {
    Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 5);

    UpdatesItemQuality.updateItem(item); // 11 -> 10
    assertThat(item.quality, is(6));

    UpdatesItemQuality.updateItem(item); // 10 -> 9
    assertThat(item.quality, is(8));

    item.sellIn = 6;
    UpdatesItemQuality.updateItem(item); // 6 -> 5
    assertThat(item.quality, is(10));

    UpdatesItemQuality.updateItem(item); // 5 -> 4
    assertThat(item.quality, is(13));

    item.sellIn = 1;
    UpdatesItemQuality.updateItem(item); // 1 -> 0
    assertThat(item.quality, is(16));

    UpdatesItemQuality.updateItem(item); // 0 -> -1
    assertThat(item.quality, is(0));
  }

  @Test
  public void backstagePassQualityDoesNotIncreaseAbove50() {
    Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(50));
  }

  @Test // @Ignore
  public void conjuredStuffDecreasesAndQualityDoesNotDecreaseBelowZero() {
    Item item = new Item("Conjured", 5, 1);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(0));
  }

  @Test // @Ignore
  public void conjuredStuffDecreasesTwoPerNight() {
    Item item = new Item("Conjured", 5, 40);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(38));
  }

  @Test // @Ignore
  public void conjuredStuffDecreasesFourWhenSellInIsNegative() {
    Item item = new Item("Conjured", -5, 40);

    UpdatesItemQuality.updateItem(item);

    assertThat(item.quality, is(36));
  }

}