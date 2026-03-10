from dotenv import load_dotenv
import logging
import asyncio

from livekit import agents, rtc
from livekit.agents import (
    AutoSubscribe,
    JobContext,
    JobRequest,
    WorkerOptions,
    llm,
)
from livekit.plugins import (
    google,
    noise_cancellation,
)

from prompt import AGENT_INSTRUCTION, PERSONALITIES, AGENT_RESPONSE

load_dotenv(".env")

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

async def entrypoint(ctx: JobContext):
    logger.info(f"starting entrypoint for room {ctx.room.name}")

    await ctx.connect(auto_subscribe=AutoSubscribe.AUDIO_ONLY)

    # Wait for the first participant to join
    participant = await ctx.wait_for_participant()
    # Get personality from participant metadata
    personality_type = participant.metadata or "Default"
    instructions = PERSONALITIES.get(personality_type, AGENT_INSTRUCTION)
    
    logger.info(f"Setting agent personality to: {personality_type}")

    agent = llm.LLMAgent(
        model=google.realtime.RealtimeModel(
            voice="Puck",
            temperature=0.8,
            instructions=instructions,
        ),
    )

    @ctx.room.on("data_received")
    def on_data_received(data: rtc.DataPacket):
        if data.topic == "file-upload":
            logger.info(f"Received file data: {len(data.data)} bytes")
            # In a real app, we would process or save this file.
            # For now, we'll just log it.

    agent.start(ctx.room, participant)

    # Maintain the session
    await asyncio.sleep(float('inf'))

if __name__ == "__main__":
    agents.cli.run_app(
        WorkerOptions(
            entrypoint_fnc=entrypoint,
        )
    )
