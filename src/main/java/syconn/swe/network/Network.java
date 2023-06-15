package syconn.swe.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import syconn.swe.Main;
import syconn.swe.network.messages.IMessage;
import syconn.swe.network.messages.MessageChange;
import syconn.swe.network.messages.MessageClickArrow;
import syconn.swe.network.messages.MessageClickTab;

import java.util.Optional;

public class Network {

    private static final String PROTOCOL_VERSION = "1";
    private static int nextId = 0;
    private static SimpleChannel playChannel;

    public static void init()
    {
        playChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Main.MODID, "play"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        register(MessageClickTab.class, new MessageClickTab(), NetworkDirection.PLAY_TO_SERVER);
        register(MessageClickArrow.class, new MessageClickArrow(), NetworkDirection.PLAY_TO_SERVER);
        register(MessageChange.class, new MessageChange(), NetworkDirection.PLAY_TO_SERVER);
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message, NetworkDirection direction)
    {
        playChannel.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle, Optional.of(direction));
    }

    public static SimpleChannel getPlayChannel()
    {
        return playChannel;
    }
}
