package hanoi.towers.data.environment

val env by lazy { js("PROCESS_ENV") }

data class Environment(
    val environment: String,
    val hanoiFrontendURL: String,
    val hanoiFrontendPort: Int,
    val hanoiBackendURL: String,
    val hanoiBackendPort: Int,
)

fun getEnv(): Environment = Environment(
    env.ENVIRONMENT,
    env.HANOI_FRONTEND_URL,
    env.HANOI_FRONTEND_PORT,
    env.HANOI_BACKEND_URL,
    env.HANOI_BACKEND_PORT,
)
