package com.trophonix.tradeplus.extras;

import com.trophonix.tradeplus.TradePlus;
import com.trophonix.tradeplus.trade.Trade;
import com.trophonix.tradeplus.util.ItemFactory;
import net.spacedelta.sdcurrency.SDCurrencyPlugin;
import net.spacedelta.sdcurrency.currency.Currency;
import net.spacedelta.sdcurrency.currency.player.PlayerCurrencyData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SDCurrencyExtra extends Extra{

    private final SDCurrencyPlugin plugin;
    private final Currency currency;

    public SDCurrencyExtra(Player player1, Player player2, TradePlus pl, Trade trade) {
        super("sdcurrency", player1, player2, pl, trade);

        plugin = (SDCurrencyPlugin) pl.getServer().getPluginManager().getPlugin("SDCurrency");
        currency = plugin.getCurrencyManager().getCurrencyByName(pl.getConfig().getString("extras.sdcurrency.currency"));
        if (currency == null) {
            pl.getLogger().severe("failed to find currency by name " + pl.getConfig().getString("extras.sdcurrency.currency"));
        }

    }

    @Override
    protected double getMax(Player player) {
        return plugin.getCurrencyDataStorage()
                .getPlayerCurrencyData(player)
                .getCurrencyEntry(currency)
                .getCurrencyAmount();
    }

    @Override
    public void onTradeEnd() {
        final PlayerCurrencyData dataOne = plugin.getCurrencyDataStorage().getPlayerCurrencyData(player1);
        final PlayerCurrencyData dataTwo = plugin.getCurrencyDataStorage().getPlayerCurrencyData(player2);

        if (value1 > 0) {
            dataOne.takeCurrency(currency, (int) value1);
            dataTwo.giveCurrency(currency, (int) value1);
        }
        if (value2 > 0) {
            dataTwo.takeCurrency(currency, (int) value2);
            dataOne.giveCurrency(currency, (int) value2);
        }

    }

    @Override
    protected ItemStack _getIcon(Player player) {
        return ItemFactory.replaceInMeta(
                icon,
                "%AMOUNT%",
                Integer.toString((int) (player.equals(player1) ? value1 : value2)),
                "%INCREMENT%",
                Integer.toString((int) increment),
                "%PLAYERINCREMENT%",
                Integer.toString((int) (player.equals(player1) ? increment1 : increment2)));
    }

    @Override
    protected ItemStack _getTheirIcon(Player player) {
        return ItemFactory.replaceInMeta(
                theirIcon, "%AMOUNT%", Integer.toString((int) (player.equals(player1) ? value1 : value2)));
    }

}
