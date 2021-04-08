<template>
    <div style="user-select: none;">
        <div class="inv-backdrop" v-if="visible"></div>
        <div class="inv-hotbar" :class="{ open: visible }" v-if="visible || hotbarFlash">
            <InventorySlot icon="hand" :materials="materials" :item="inventory.hotbar[0]" @click="e => clickSlot('hotbar', 0, e)" />
            <InventorySlot icon="pistol" :materials="materials" :item="inventory.hotbar[1]" @click="e => clickSlot('hotbar', 1, e)" />
            <InventorySlot icon="pistol" :materials="materials" :item="inventory.hotbar[2]" @click="e => clickSlot('hotbar', 2, e)" />
            <InventorySlot icon="tools" :materials="materials" :item="inventory.hotbar[3]" @click="e => clickSlot('hotbar', 3, e)" />
        </div>
        <div class="inv-main" v-if="visible">
            Inventory
            <br>
            <table>
                <tbody>
                    <tr v-for="(_, row) of new Array(5)" :key="row">
                        <td width="80">
                            <InventorySlot v-if="row == 0" :materials="materials" :item="inventory.clothing[0]" @click="e => clickSlot('clothing', 0, e)" icon="redhat" />
                            <InventorySlot v-if="row == 1" :materials="materials" :item="inventory.clothing[1]" @click="e => clickSlot('clothing', 1, e)" icon="tshirt-crew-outline" />
                            <InventorySlot v-if="row == 2" :materials="materials" :item="inventory.clothing[2]" @click="e => clickSlot('clothing', 2, e)" icon="seat-legroom-extra" />
                            <InventorySlot v-if="row == 3" :materials="materials" :item="inventory.clothing[3]" @click="e => clickSlot('clothing', 3, e)" icon="shoe-sneaker" />
                        </td>
                        <td v-for="(_, col) of new Array(6)" :key="col">
                            <InventorySlot :materials="materials" :item="inventory.generic[(row*6)+col]" @click="e => clickSlot('generic', (row*6)+col, e)" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="inv-container" v-if="visible">
            {{ container.title }}
            <br>
            <div class="items">
                <table>
                    <tbody style="height: 40px; overflow: scroll;">
                        <tr v-for="(_, row) of new Array(Math.floor(container.size/6))" :key="row">
                            <td v-for="(_, col) of new Array(6)" :key="col">
                                <InventorySlot :materials="materials" :item="container.items[(row*6)+col]" @click="e => clickSlot('container', (row*6)+col, e)" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="inv-cursor" v-if="visible && inventory.cursor" :style="{ left: (mouse.x - 25) + 'px', top: (mouse.y - 30) + 'px' }">
            <div class="amount">{{ inventory.cursor.amount || 1 }}</div>
            <img :src="getItemImage(inventory.cursor.type)" width="50" height="50">
        </div>
    </div>
</template>

<script>
import InventorySlot from './InventorySlot'

export default {
    name: 'Inventory',
    components: {
        InventorySlot
    },
    data() {
        return {
            visible: true,
            materials: {
                DEMO_APPLE: {
                    name: 'Apple',
                    category: 'FOOD',
                    max: 10
                },
                DEMO_STEAK: {
                    name: 'Steak',
                    category: 'FOOD',
                    max: 3
                }
            },
            hotbarFlash: false,
            hotbarSlot: 0,
            hotbarTimer: null,
            mouse: {
                x: 0,
                y: 0
            },
            popover: null,
            inventory: {
                clothing: [],
                generic: [null, null, null, null, null, null, null, { type: 'DEMO_APPLE', amount: 30, metadata: {} }, { type: 'DEMO_STEAK', amount: 5, metadata: {} }],
                hotbar: [],
                cursor: null
            },
            container: {
                title: 'Trunk',
                size: 40,
                items: []
            }
        }
    },
    created() {
        window.addEventListener('mousemove', this.mousemove)
        this.bus.$on('onset:inventory:materials', materials => this.materials = materials)
        this.bus.$on('onset:inventory:character:update', inventory => this.inventory = inventory)
        this.bus.$on('onset:inventory:container:update', container => this.container = container)
        this.bus.$on('onset:inventory:open', () => this.visible = true)
        this.bus.$on('onset:inventory:close', () => this.visible = false)
        this.bus.$on('onset:inventory:hotbar:switch', slot => {
            this.hotbarSlot = slot
            if(!this.visible) {
                if(this.hotbarTimer)
                    clearTimeout(this.hotbarTimer)
                this.hotbarFlash = true
                this.hotbarTimer = setTimeout(() => {
                    this.hotbarFlash = false
                    this.hotbarTimer = null
                }, 3000)
            }
        })
    },
    beforeDestroy() {
        window.removeEventListener('mousemove', this.mousemove)
    },
    methods: {
        mousemove(e) {
            this.mouse.x = e.clientX
            this.mouse.y = e.clientY
        },
        isSimilar(a, b) {
            return a === b || a.type === b.type
        },
        addable(area, slot, target, item, amount) {
            if(target != null && !this.isSimilar(target, item))
                return 0
            if(area === 'clothing') {
                if(slot === 0 && !item.hat)
                    return 0
                if(slot === 1 && !item.top)
                    return 0
                if(slot === 2 && !item.pants)
                    return 0
                if(slot === 3 && !item.shoes)
                    return 0
            }
            if(area === 'hotbar') {
                if(slot === 0)
                    return 0
                if(slot < 3 && !item.gun)
                    return 0
                if(!item.usable)
                    return 0
            }
            if(target == null)
                return Math.min(item.amount || 1, amount)
            return Math.max(0, Math.min(this.materials[target.type].max || 64, item.amount || 1, amount))
        },
        clickSlot(area, slot, e) {
            if(!this.visible)
                return
            const areaArray = area === 'container' ? this.container.items : this.inventory[area]
            if(this.inventory.cursor) {
                let amount = this.addable(area, slot, areaArray[slot], this.inventory.cursor, e.button === 'right' ? 1 : (this.inventory.cursor.amount || 1))
                if(amount === 0 && areaArray[slot]) {
                    // Swap
                    amount = this.addable(area, slot, null, this.inventory.cursor, e.button === 'right' ? 1 : (this.inventory.cursor.amount || 1))
                    if(amount > 0) {
                        const old = areaArray[slot]
                        areaArray[slot] = this.inventory.cursor
                        this.inventory.cursor = old
                    }
                    return
                }
                if(amount > 0) {
                    // Put
                    if(!areaArray[slot])
                        areaArray[slot] = { ...JSON.parse(JSON.stringify(this.inventory.cursor)), amount: 0 }
                    areaArray[slot].amount += amount
                    this.inventory.cursor.amount -= amount
                    if(this.inventory.cursor.amount === 0)
                        this.inventory.cursor = null
                }
            } else {
                // Pick
                if(e.button === 'right' && areaArray[slot] && areaArray[slot].amount > 1) {
                    this.inventory.cursor = JSON.parse(JSON.stringify(areaArray[slot]))
                    this.inventory.cursor.amount = Math.floor(areaArray[slot].amount / 2)
                    areaArray[slot].amount -= this.inventory.cursor.amount
                } else {
                    this.inventory.cursor = areaArray[slot]
                    areaArray[slot] = null
                }
            }
            this.$forceUpdate()
        },
        getItemImage(item) {
            const items = require.context('../assets/img/items/', false, /\.png$/)
            return items('./' + item.toLowerCase() + ".png")
        }
    }
}
</script>

<style lang="scss" scoped>
.inv-backdrop {
    background-color: black;
    opacity: 0.6;
    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    transition-property: opacity;
    transition-duration: 1s;
}
.inv-cursor {
    position: absolute;
    pointer-events: none;
    width: 60px;
    height: 60px;
    .amount {
        color: white;
        position: absolute;
        bottom: 0px;
        right: 5px;
    }
}
.inv-hotbar {
    position: absolute;
    left: 50%;
    bottom: 5px;
    transform: translateX(-50%);
    border-radius: 5px;
    padding: 2px;
    .slot {
        display: inline-block;
        height: 60px;
        width: 60px;
        border-radius: 5px;
        background-color: rgba(100, 100, 100, 0.5);
        margin: 5px;
    }
    &.open {
        background-color: rgba(50, 50, 50, 0.8);
    }
}
.inv-main {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    background-color: rgba(50, 50, 50, 0.8);
    color: white;
    padding: 5px;
    padding-left: 15px;
    padding-right: 15px;
    border-radius: 5px;
    padding-bottom: 10px;
    padding-top: 10px;
}
.inv-container {
    position: absolute;
    left: 70px;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(50, 50, 50, 0.8);
    color: white;
    padding: 5px;
    padding-left: 15px;
    padding-right: 15px;
    border-radius: 5px;
    padding-bottom: 10px;
    padding-top: 10px;
    .items {
        height: 300px;
        overflow-y: scroll;
    }
    .items::-webkit-scrollbar {
        width: 0px;
    }
}
</style>