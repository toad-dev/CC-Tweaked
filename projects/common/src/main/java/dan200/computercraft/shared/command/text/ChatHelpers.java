// SPDX-FileCopyrightText: 2017 The CC: Tweaked Developers
//
// SPDX-License-Identifier: MPL-2.0

package dan200.computercraft.shared.command.text;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;

/**
 * Various helpers for building chat messages.
 */
public final class ChatHelpers {
    private static final ChatFormatting HEADER = ChatFormatting.LIGHT_PURPLE;

    private ChatHelpers() {
    }

    public static MutableComponent coloured(@Nullable String text, ChatFormatting colour) {
        return Component.literal(text == null ? "" : text).withStyle(colour);
    }

    public static <T extends MutableComponent> T coloured(T component, ChatFormatting colour) {
        component.withStyle(colour);
        return component;
    }

    public static MutableComponent text(@Nullable String text) {
        return Component.literal(text == null ? "" : text);
    }

    public static MutableComponent translate(@Nullable String text) {
        return Component.translatable(text == null ? "" : text);
    }

    public static MutableComponent translate(@Nullable String text, Object... args) {
        return Component.translatable(text == null ? "" : text, args);
    }

    public static MutableComponent list(Component... children) {
        var component = Component.literal("");
        for (var child : children) {
            component.append(child);
        }
        return component;
    }

    public static MutableComponent position(@Nullable BlockPos pos) {
        if (pos == null) return translate("commands.computercraft.generic.no_position");
        return translate("commands.computercraft.generic.position", pos.getX(), pos.getY(), pos.getZ());
    }

    public static MutableComponent bool(boolean value) {
        return value
            ? coloured(translate("commands.computercraft.generic.yes"), ChatFormatting.GREEN)
            : coloured(translate("commands.computercraft.generic.no"), ChatFormatting.RED);
    }

    public static Component link(MutableComponent component, String command, Component toolTip) {
        return link(component, new ClickEvent(ClickEvent.Action.RUN_COMMAND, command), toolTip);
    }

    public static Component link(Component component, ClickEvent click, Component toolTip) {
        var style = component.getStyle();

        if (style.getColor() == null) style = style.withColor(ChatFormatting.YELLOW);
        style = style.withClickEvent(click);
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, toolTip));

        return component.copy().withStyle(style);
    }

    public static MutableComponent header(String text) {
        return coloured(text, HEADER);
    }

    public static MutableComponent copy(String text) {
        var name = Component.literal(text);
        var style = name.getStyle()
            .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text))
            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("gui.computercraft.tooltip.copy")));
        return name.withStyle(style);
    }
}
