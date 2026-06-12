# Claude Configuration Directory

This directory contains AI assistant configuration and skill files for the Hunter Service project.

## Structure
```
.claude/
├── skills/
│   ├── .cursorrules      # Cursor AI configuration
│   └── .aiinstructions   # Universal AI instructions
└── README.md             # This file
```

## Important Notes
- **CLAUDE.md** remains at project root for automatic loading by Claude Code
- Skills folder contains AI tool-specific instructions
- All AI assistants should read these before making code changes

## Usage
- Claude Code automatically reads `/CLAUDE.md` from project root
- Cursor reads `.cursorrules` from this skills folder
- Other AI tools (Copilot, Cody, Antigravity) use `.aiinstructions`