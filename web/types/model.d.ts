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
  fullname: string
  job: string
}

interface User {
  username?: string
  email?: string
  firstName?: string
  lastName?: string
  birthday?: string
  avatar?: string
}
