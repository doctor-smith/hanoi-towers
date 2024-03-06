package hanoi.towers.data.cookies

import lib.optics.lens.Lens


data class Cookies(
    val isCookieDisclaimerConfirmed: Boolean,
)

val isCookieDisclaimerConfirmed: Lens<Cookies, Boolean> = Lens(
    {data -> data.isCookieDisclaimerConfirmed},
    {s: Boolean -> {data -> data.copy(isCookieDisclaimerConfirmed = s)}}
)