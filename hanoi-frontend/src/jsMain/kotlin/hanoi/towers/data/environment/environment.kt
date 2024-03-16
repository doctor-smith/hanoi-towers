package hanoi.towers.data.environment

val env by lazy { js("PROCESS_ENV") }

data class Environment(
    val environment: String,
    val hanoiBackendURL: String,
    val hanoiBackendPort: Int
)

fun getEnv(): Environment = Environment(
    env.ENVIRONMENT,
    env.HANOI_BACKEND_URL,
    env.HANOI_BACKEND_PORT,
)