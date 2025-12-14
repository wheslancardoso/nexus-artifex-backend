# SSE Events Contract

> Contrato estável de eventos Server-Sent Events (SSE) do Nexus Artifex.

## Endpoint de Streaming

```
GET /api/v1/projects/{projectId}/stream
Content-Type: text/event-stream
```

**Comportamento:**
- Timeout: Infinito (`0L`)
- Uma conexão por cliente
- Eventos entregues apenas para o `projectId` especificado

---

## Eventos

### 1. `connected`

**Trigger:** Imediatamente após conexão SSE estabelecida.

```json
event: connected
data: {
  "status": "connected",
  "projectId": "uuid"
}
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `status` | string | Sempre `"connected"` |
| `projectId` | string (UUID) | ID do projeto conectado |

---

### 2. `node.created`

**Trigger:** Após criação manual de um Node via `POST /api/v1/projects/{projectId}/nodes`.

```json
event: node.created
data: {
  "type": "node.created",
  "projectId": "uuid",
  "node": {
    "id": "uuid",
    "type": "CONCEPT|MUTATION",
    "label": "string",
    "summary": "string"
  }
}
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `type` | string | Sempre `"node.created"` |
| `projectId` | string (UUID) | ID do projeto |
| `node.id` | string (UUID) | ID do node criado |
| `node.type` | string | Tipo do node (`CONCEPT`, `MUTATION`) |
| `node.label` | string | Label do node |
| `node.summary` | string | Resumo (pode ser vazio) |

---

### 3. `node.evolved`

**Trigger:** Após evolução SCAMPER via `POST /api/v1/nodes/{nodeId}/evolve`.

```json
event: node.evolved
data: {
  "type": "node.evolved",
  "projectId": "uuid",
  "technique": "SUBSTITUTE|COMBINE|ADAPT|MODIFY|PUT_TO_ANOTHER_USE|ELIMINATE|REVERSE",
  "node": {
    "id": "uuid",
    "label": "string",
    "summary": "string"
  },
  "edge": {
    "source": "uuid",
    "target": "uuid",
    "relationship": "EVOLVED_{TECHNIQUE}"
  }
}
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `type` | string | Sempre `"node.evolved"` |
| `projectId` | string (UUID) | ID do projeto |
| `technique` | string | Técnica SCAMPER utilizada |
| `node.*` | object | Node evoluído criado |
| `edge.*` | object | Edge conectando original ao evoluído |

---

## Regras de Entrega

1. **Fan-out por projeto:** Eventos são entregues apenas para conexões do mesmo `projectId`.
2. **Sem eventos em erro:** Se uma operação falhar, nenhum evento é emitido.
3. **Emitters inválidos:** Conexões que falharem são removidas automaticamente.
4. **Ordem:** Eventos são entregues na ordem em que ocorrem.

---

## Exemplo de Uso (JavaScript)

```javascript
const eventSource = new EventSource('/api/v1/projects/{projectId}/stream');

eventSource.addEventListener('connected', (e) => {
  console.log('Conectado:', JSON.parse(e.data));
});

eventSource.addEventListener('node.created', (e) => {
  const data = JSON.parse(e.data);
  // Adicionar node ao grafo
});

eventSource.addEventListener('node.evolved', (e) => {
  const data = JSON.parse(e.data);
  // Adicionar node e edge ao grafo
});
```
