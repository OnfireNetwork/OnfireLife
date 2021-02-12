<template>
    <div class="slot"
        @mouseout="popover = null"
        @mousemove="e => popover = { x: e.offsetX, y: e.offsetY }"
        @mouseenter="e => popover = { x: e.offsetX, y: e.offsetY }"
        @click.right.stop.prevent="e => $emit('click', { button: 'right' })"
        @click.stop.prevent="e => $emit('click', { button: 'left' })"
        :style="{ 'background-color': this.popover ? 'rgba(150, 150, 150, 0.5)' : 'rgba(100, 100, 100, 0.5)' }"
    >
        <v-icon v-if="icon" class="centered" style=" color: rgba(50, 50, 50, 0.5)" x-large>mdi-{{ icon }}</v-icon>
        <img v-if="item" class="centered" :src="getItemImage(item.type)" width="50" height="50">
        <div class="amount" v-if="item">{{ item.amount || 1 }}</div>
        <div class="popover" v-if="popover && item" :style="{ left: (popover.x + 25) + 'px', top: (popover.y - 5) + 'px' }">{{ item.name }}</div>
    </div>
</template>

<script>
export default {
    props: {
        icon: {
            type: String,
            required: false
        },
        item: {
            type: Object,
            required: false
        }
    },
    data() {
        return {
            popover: null
        }
    },
    methods: {
        getItemImage(item) {
            const items = require.context('../assets/img/items/', false, /\.png$/)
            return items('./' + item + ".png")
        }
    }
}
</script>

<style lang="scss" scoped>
.slot {
    .centered {
        -webkit-user-drag: none;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
    cursor: pointer;
    position: relative;
    height: 60px;
    width: 60px;
    border-radius: 5px;
    margin: 5px;
    .amount {
        color: white;
        position: absolute;
        bottom: 0px;
        right: 5px;
    }
    .popover {
        z-index: 100;
        position: absolute;
        pointer-events: none;
        border-radius: 5px;
        background-color: rgb(22, 181, 255);
        color: white;
        padding: 5px;
    }
}
</style>