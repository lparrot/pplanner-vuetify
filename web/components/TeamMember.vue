<template>
  <div class="p-2 lg:w-1/3 md:w-1/2 w-full">
    <div class="h-full flex items-center border-gray-200 border p-4 rounded-lg">
      <img :src="$gravatar.url(member.avatar)" alt="team" class="w-16 h-16 bg-gray-100 object-cover object-center flex-shrink-0 rounded-full mr-4">
      <div class="flex-grow">
        <h2 class="text-gray-900 title-font font-medium">{{ member.fullname }}</h2>
        <p class="text-gray-500 text-sm">{{ member.job }}</p>
      </div>

      <div class="flex justify-end">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TeamMember',

  props: {
    member: {
      type: Object,
      required: true,
    },
  },
}
</script>
