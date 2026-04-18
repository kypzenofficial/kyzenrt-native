# KyzenRT Native

Universal edge AI inference runtime for iOS and Android — by [Kypzen](https://kypzen.com)

Built on [llama.cpp](https://github.com/ggml-org/llama.cpp) (MIT).

## What is KyzenRT?

KyzenRT is Kypzen's open-source edge AI inference runtime. It runs large language models and vision AI models fully offline on any iOS or Android device, with automatic hardware acceleration across Snapdragon NPU, Apple Neural Engine, ARM NEON, and CPU.

## Key features

- Offline LLM inference — no internet required after model download
- Auto hardware tier detection (low / mid / high device)
- Thermal adaptation — auto-throttles batch size under thermal pressure  
- Sliding context window — conversations never crash at context limit
- Session keep-alive — model persists across app switches
- Supports GGUF format (Q4_K_M, Q5_K_M, Q8_0)

## Install

```bash
npm install @kypzen/kyzenrt-native
```

## Quick start

```typescript
import { initKyzenRT } from '@kypzen/kyzenrt-native';

const ctx = await initKyzenRT({ model: '/path/to/model.gguf' });
const result = await ctx.completion({
  messages: [{ role: 'user', content: 'Hello from KyzenRT!' }]
});
```

## Contributors

| GitHub | Role |
|--------|------|
| [kypzenofficial](https://github.com/kypzenofficial) | Maintainer |
| [prkshtshrm4](https://github.com/prkshtshrm4) | Core contributor |

## Licence

MIT — see [LICENSE](./LICENSE)

Upstream attribution:  
[mybigday/llama.rn](https://github.com/mybigday/llama.rn) MIT  
[ggml-org/llama.cpp](https://github.com/ggml-org/llama.cpp) MIT
