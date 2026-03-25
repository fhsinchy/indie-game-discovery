package dev.farhan.discovery.seeder;

import dev.farhan.discovery.domain.Game;
import dev.farhan.discovery.repository.GameRepository;
import dev.farhan.discovery.service.EmbeddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final GameRepository gameRepository;
    private final EmbeddingService embeddingService;

    public DataSeeder(GameRepository gameRepository, EmbeddingService embeddingService) {
        this.gameRepository = gameRepository;
        this.embeddingService = embeddingService;
    }

    @Override
    public void run(String... args) {
        if (gameRepository.count() == 0) {
            logger.info("Seeding games collection...");

            List<Game> games = List.of(
                    new Game("Hollow Knight",
                            "A challenging 2D action-adventure through a vast interconnected underground kingdom filled with insects. Players explore winding caverns, battle corrupted creatures, and uncover an ancient mystery.",
                            "Team Cherry",
                            List.of("metroidvania", "action", "platformer"),
                            List.of("atmospheric", "difficult", "exploration", "hand-drawn"),
                            List.of("ability-unlocks", "backtracking", "boss-fights"),
                            4.7, 2017),

                    new Game("Slay the Spire",
                            "A fusion of card games and roguelikes where players craft unique decks, encounter bizarre creatures, and discover relics of immense power to fight their way to the top of the Spire.",
                            "Mega Crit Games",
                            List.of("roguelike", "strategy", "card-game"),
                            List.of("replayable", "turn-based", "procedural"),
                            List.of("deck-building", "permadeath", "procedural-generation"),
                            4.8, 2019),

                    new Game("Hades",
                            "A rogue-like dungeon crawler where players defy the god of the dead as they hack and slash through the Underworld of Greek myth. Each run reveals more of the story and unlocks new powers.",
                            "Supergiant Games",
                            List.of("roguelike", "action", "hack-and-slash"),
                            List.of("fast-paced", "mythology", "replayable"),
                            List.of("permadeath", "procedural-generation", "upgrade-system"),
                            4.9, 2020),

                    new Game("Dead Cells",
                            "A roguelike metroidvania where players explore an ever-changing castle, fighting through procedurally generated levels with tight, responsive combat and permanent upgrades between runs.",
                            "Motion Twin",
                            List.of("roguelike", "action", "metroidvania"),
                            List.of("fast-paced", "difficult", "pixel-art"),
                            List.of("permadeath", "procedural-generation", "ability-unlocks"),
                            4.6, 2018),

                    new Game("Outer Wilds",
                            "An open-world mystery set in a solar system trapped in a time loop. Players explore planets, translate alien text, and piece together the story of a vanished civilization before the sun explodes.",
                            "Mobius Digital",
                            List.of("adventure", "exploration", "puzzle"),
                            List.of("space", "mystery", "narrative", "open-world"),
                            List.of("time-loop", "exploration", "environmental-storytelling"),
                            4.8, 2019),

                    new Game("Celeste",
                            "A narrative-driven platformer about climbing a mountain. Players guide Madeline through punishing precision challenges while uncovering a heartfelt story about self-discovery and perseverance.",
                            "Maddy Makes Games",
                            List.of("platformer", "adventure"),
                            List.of("difficult", "pixel-art", "narrative", "atmospheric"),
                            List.of("precision-jumping", "dash-mechanics", "assist-mode"),
                            4.8, 2018),

                    new Game("Stardew Valley",
                            "A farming simulation where players inherit a run-down farm and build it into a thriving homestead. Grow crops, raise animals, mine for resources, and befriend the townsfolk of Pelican Town.",
                            "ConcernedApe",
                            List.of("simulation", "RPG"),
                            List.of("relaxing", "farming", "pixel-art", "open-ended"),
                            List.of("crafting", "relationship-building", "resource-management"),
                            4.8, 2016),

                    new Game("Undertale",
                            "An RPG where players navigate the underground realm of monsters with a unique combat system that lets them choose mercy over violence. Every choice shapes the story and its outcome.",
                            "Toby Fox",
                            List.of("RPG", "adventure"),
                            List.of("narrative", "pixel-art", "quirky", "emotional"),
                            List.of("turn-based-combat", "bullet-hell", "moral-choices"),
                            4.7, 2015),

                    new Game("Cuphead",
                            "A run-and-gun action game styled after 1930s cartoons. Players fight through relentless boss battles and side-scrolling levels with hand-drawn animation and a jazz soundtrack.",
                            "Studio MDHR",
                            List.of("action", "platformer"),
                            List.of("difficult", "hand-drawn", "retro", "co-op"),
                            List.of("boss-fights", "pattern-recognition", "run-and-gun"),
                            4.6, 2017),

                    new Game("Into the Breach",
                            "A turn-based strategy game where players control mechs to defend cities from giant creatures. Every move matters in this compact tactical puzzle with roguelike progression.",
                            "Subset Games",
                            List.of("strategy", "roguelike"),
                            List.of("turn-based", "tactical", "minimalist", "replayable"),
                            List.of("tactical-combat", "permadeath", "grid-based"),
                            4.5, 2018),

                    new Game("Return of the Obra Dinn",
                            "A first-person mystery where players use a magical pocket watch to investigate the fate of every soul aboard a lost merchant ship. Each death is a logic puzzle to solve through deduction.",
                            "Lucas Pope",
                            List.of("puzzle", "adventure"),
                            List.of("mystery", "detective", "atmospheric", "monochrome"),
                            List.of("deduction", "exploration", "environmental-storytelling"),
                            4.7, 2018),

                    new Game("Disco Elysium",
                            "A groundbreaking RPG where players solve a murder case in a decaying city through dialogue and thought. There is no combat, only conversations, skill checks, and an internal chorus of personality traits.",
                            "ZA/UM",
                            List.of("RPG", "adventure"),
                            List.of("narrative", "detective", "atmospheric", "open-world"),
                            List.of("dialogue-driven", "skill-checks", "branching-narrative"),
                            4.7, 2019),

                    new Game("Ori and the Blind Forest",
                            "A visually stunning platformer set in a dying forest. Players guide a small guardian spirit through dangerous terrain, unlocking new abilities and restoring life to the world through fluid movement.",
                            "Moon Studios",
                            List.of("platformer", "metroidvania", "adventure"),
                            List.of("atmospheric", "emotional", "beautiful", "hand-drawn"),
                            List.of("ability-unlocks", "precision-jumping", "backtracking"),
                            4.6, 2015),

                    new Game("Shovel Knight",
                            "A retro-styled action platformer where players wield a shovel to dig through dirt, defeat enemies, and bounce off foes across treacherous stages inspired by classic NES games.",
                            "Yacht Club Games",
                            List.of("platformer", "action"),
                            List.of("retro", "pixel-art", "challenging", "nostalgic"),
                            List.of("melee-combat", "bouncing-mechanics", "level-based"),
                            4.5, 2014),

                    new Game("Hyper Light Drifter",
                            "A top-down action RPG set in a beautiful, ruined world. Players fight through punishing combat encounters, discover forgotten technologies, and unravel a wordless narrative told through pixel art.",
                            "Heart Machine",
                            List.of("action", "RPG", "adventure"),
                            List.of("pixel-art", "atmospheric", "difficult", "exploration"),
                            List.of("combat", "dash-mechanics", "exploration"),
                            4.5, 2016),

                    new Game("Katana ZERO",
                            "A neo-noir action platformer with instant-death combat and time manipulation. Players slash through enemies as a samurai assassin, piecing together a fragmented story between missions.",
                            "Askiisoft",
                            List.of("action", "platformer"),
                            List.of("narrative", "neo-noir", "fast-paced", "stylish"),
                            List.of("time-manipulation", "one-hit-kills", "melee-combat"),
                            4.5, 2019),

                    new Game("Inscryption",
                            "A card-based odyssey that blends deckbuilding roguelike, escape-room puzzles, and psychological horror into a constantly shifting experience that defies expectations at every turn.",
                            "Daniel Mullins Games",
                            List.of("card-game", "horror", "puzzle"),
                            List.of("creepy", "mysterious", "meta", "replayable"),
                            List.of("deck-building", "puzzle-solving", "sacrifice-mechanics"),
                            4.7, 2021),

                    new Game("Vampire Survivors",
                            "A minimalist gothic horror survival game where hordes of monsters swarm the screen and players mow them down with auto-attacking weapons that grow increasingly powerful over time.",
                            "poncle",
                            List.of("action", "roguelike", "survival"),
                            List.of("addictive", "chaotic", "pixel-art", "casual"),
                            List.of("auto-combat", "upgrade-system", "wave-survival"),
                            4.5, 2022),

                    new Game("Tunic",
                            "An isometric action adventure starring a small fox in a vast, mysterious world. Players discover secrets, fight challenging enemies, and piece together an in-game instruction manual written in a lost language.",
                            "Andrew Shouldice",
                            List.of("action", "adventure", "puzzle"),
                            List.of("exploration", "mysterious", "charming", "challenging"),
                            List.of("combat", "puzzle-solving", "exploration"),
                            4.6, 2022),

                    new Game("Baba Is You",
                            "A puzzle game where the rules are physical objects on the playing field. Players push word blocks around to change how the level works, turning walls into goals and enemies into allies.",
                            "Hempuli",
                            List.of("puzzle", "strategy"),
                            List.of("minimalist", "clever", "mind-bending", "innovative"),
                            List.of("rule-manipulation", "logic-puzzles", "push-mechanics"),
                            4.6, 2019),

                    new Game("Spelunky 2",
                            "A roguelike platformer where players descend into procedurally generated caves filled with traps, treasures, and secrets. Every run is different, and one wrong step means starting over.",
                            "Mossmouth",
                            List.of("roguelike", "platformer"),
                            List.of("procedural", "difficult", "replayable", "co-op"),
                            List.of("permadeath", "procedural-generation", "physics-based"),
                            4.4, 2020),

                    new Game("Risk of Rain 2",
                            "A third-person roguelike shooter where players fight through alien landscapes, collecting items that stack into absurd combinations of power before facing overwhelming odds.",
                            "Hopoo Games",
                            List.of("roguelike", "action", "shooter"),
                            List.of("multiplayer", "chaotic", "replayable", "sci-fi"),
                            List.of("procedural-generation", "item-stacking", "permadeath", "co-op-gameplay"),
                            4.5, 2020),

                    new Game("Darkest Dungeon",
                            "A challenging gothic roguelike RPG about the stresses of dungeon crawling. Players recruit, train, and lead a team of flawed heroes through procedural dungeons where stress and madness are constant threats.",
                            "Red Hook Studios",
                            List.of("roguelike", "RPG", "strategy"),
                            List.of("dark", "difficult", "gothic", "stressful"),
                            List.of("permadeath", "stress-management", "turn-based-combat", "party-management"),
                            4.4, 2016),

                    new Game("Transistor",
                            "An action RPG set in a stunning sci-fi city where players wield a powerful weapon called the Transistor. Combine abilities in a flexible system that blends real-time action with strategic planning.",
                            "Supergiant Games",
                            List.of("action", "RPG"),
                            List.of("sci-fi", "atmospheric", "beautiful", "narrative"),
                            List.of("tactical-combat", "ability-combining", "pause-and-plan"),
                            4.5, 2014),

                    new Game("Bastion",
                            "An action RPG set in a world shattered by a catastrophic event. Players carve a path through vibrant, floating ruins while a gravelly narrator reacts to every move in real time.",
                            "Supergiant Games",
                            List.of("action", "RPG"),
                            List.of("narrative", "colorful", "atmospheric", "post-apocalyptic"),
                            List.of("hack-and-slash", "weapon-customization", "dynamic-narration"),
                            4.4, 2011)
            );

            gameRepository.saveAll(games);
            logger.info("Seeded {} games.", games.size());
        } else {
            logger.info("Games collection already has data. Skipping seed.");
        }

        List<Game> allGames = gameRepository.findAll();
        int generated = 0;

        for (Game game : allGames) {
            if (game.getEmbedding() == null) {
                float[] embedding = embeddingService.generateGameEmbedding(game);
                game.setEmbedding(embedding);
                gameRepository.save(game);
                generated++;
            }
        }

        if (generated > 0) {
            logger.info("Generated embeddings for {} games.", generated);
        } else {
            logger.info("All games already have embeddings.");
        }
    }
}
