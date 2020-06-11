<template>
  <div class="user-avatar-dropdown" style="margin-right: 10px">
    <Dropdown @on-click="handleClick">
      <Badge :dot="!messageUnreadCount">
        <Avatar src="https://i.loli.net/2017/08/21/599a521472424.jpg"/>
      </Badge>
      <Icon :size="18" type="md-arrow-dropdown"></Icon>
      <DropdownMenu slot="list">
        <DropdownItem name="message">
          消息中心<Badge style="margin-left: 10px" :count="messageUnreadCount"></Badge>
        </DropdownItem>
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
    </Dropdown>
  </div>
</template>

<script>
import './user.less'
export default {
  name: 'User',
  props: {
    userAvatar: {
      type: String,
      default: 'https://i.loli.net/2017/08/21/599a521472424.jpg'
    },
    messageUnreadCount: {
      type: Number,
      default: 0
    }
  },
  methods: {
    logout () {
        this.$router.push({
            name: 'login'
        })
    },
    message () {

    },
    handleClick (name) {
      switch (name) {
        case 'logout': this.logout()
          break
        case 'message': this.message()
          break
      }
    }
  }
}
</script>
