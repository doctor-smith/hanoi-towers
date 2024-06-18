import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

// Datenklassen für API-Typen
data class Slice(val id: Int, val size: Int)
data class Tower(val id: Int, val slices: List<Slice>)
data class Hanoi(val id: Int, val towerOne: Tower, val towerTwo: Tower, val towerThree: Tower)

// Klassen für Entitäten
class SliceEntity(val id: Int, val size: Int)
class TowerEntity(val id: Int, val slices: List<SliceEntity>)
class HanoiEntity(val id: Int, val towerOne: TowerEntity, val towerTwo: TowerEntity, val towerThree: TowerEntity)

// Konvertierungsfunktionen
fun SliceEntity.toApiType() = Slice(id, size)
fun TowerEntity.toApiType() = Tower(id, slices.map { it.toApiType() })
fun HanoiEntity.toApiType() = Hanoi(id, towerOne.toApiType(), towerTwo.toApiType(), towerThree.toApiType())

class TransformTest {

    @Test
    fun testTransformations() {
        testSliceEntityToApiType()
        testTowerEntityToApiType()
        testHanoiEntityToApiType()
        println("All tests passed")
    }

    fun testSliceEntityToApiType() {
        val sliceEntity = SliceEntity(1, 5)
        val sliceApi = sliceEntity.toApiType()

        assertNotNull(sliceApi.id)
        assertEquals(5, sliceApi.size)
        println("testSliceEntityToApiType passed")
    }

    fun testTowerEntityToApiType() {
        val sliceEntity1 = SliceEntity(1, 5)
        val sliceEntity2 = SliceEntity(2, 3)
        val towerEntity = TowerEntity(1, listOf(sliceEntity1, sliceEntity2))
        val towerApi = towerEntity.toApiType()

        // Überprüfung, ob die ID und die IDs der Slices existieren
        assertNotNull(towerApi.id)
        assertEquals(2, towerApi.slices.size)
        assertNotNull(towerApi.slices[0].id)
        assertEquals(5, towerApi.slices[0].size)
        assertNotNull(towerApi.slices[1].id)
        assertEquals(3, towerApi.slices[1].size)
        println("testTowerEntityToApiType passed")
    }

    fun testHanoiEntityToApiType() {
        val sliceEntity1 = SliceEntity(1, 5)
        val sliceEntity2 = SliceEntity(2, 3)
        val sliceEntity3 = SliceEntity(3, 1)

        val towerEntity1 = TowerEntity(1, listOf(sliceEntity1))
        val towerEntity2 = TowerEntity(2, listOf(sliceEntity2))
        val towerEntity3 = TowerEntity(3, listOf(sliceEntity3))

        val hanoiEntity = HanoiEntity(1, towerEntity1, towerEntity2, towerEntity3)
        val hanoiApi = hanoiEntity.toApiType()

        assertNotNull(hanoiApi.id)
        assertNotNull(hanoiApi.towerOne.id)
        assertNotNull(hanoiApi.towerOne.slices[0].id)
        assertEquals(5, hanoiApi.towerOne.slices[0].size)
        assertNotNull(hanoiApi.towerTwo.id)
        assertNotNull(hanoiApi.towerTwo.slices[0].id)
        assertEquals(3, hanoiApi.towerTwo.slices[0].size)
        assertNotNull(hanoiApi.towerThree.id)
        assertNotNull(hanoiApi.towerThree.slices[0].id)
        assertEquals(1, hanoiApi.towerThree.slices[0].size)
        println("testHanoiEntityToApiType passed")
    }
}
