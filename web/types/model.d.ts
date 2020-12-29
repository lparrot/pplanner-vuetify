interface Project {
  id?: string
  name: string
  boards: Array<Board>
}

interface Board {
  id: string
  name: string
  description: string
  visibility: string
}

interface BoardModule {
  type: string
  name: string
}

interface Member {
  avatar: string
  job: string
  firstName?: string
  lastName?: string
  fullname?: string
  birthday?: string
  email?: string
}

interface User {
  email?: string
  member?: Member
  username?: string
}
