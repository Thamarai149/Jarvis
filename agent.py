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

from prompt import AGENT_INSTRUCTION, AGENT_RESPONSE

load_dotenv(".env")

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

async def entrypoint(ctx: JobContext):
    logger.info(f"starting entrypoint for room {ctx.room.name}")

    await ctx.connect(auto_subscribe=AutoSubscribe.AUDIO_ONLY)

    # Wait for the first participant to join
    participant = await ctx.wait_for_participant()
    
    logger.info("Starting voice assistant agent")

    agent = llm.LLMAgent(
        model=google.realtime.RealtimeModel(
            voice="Puck",
            temperature=0.8,
            instructions=AGENT_INSTRUCTION,
        ),
    )

    agent.start(ctx.room, participant)

    # Maintain the session
    await asyncio.sleep(float('inf'))

if __name__ == "__main__":
    agents.cli.run_app(
        WorkerOptions(
            entrypoint_fnc=entrypoint,
        )
    )
