interface Project {
  id?: string
  name: string
  boards: Array<Board>
}

interface Board {
  id: string
  name: string
  visibility?: string
}

interface BoardModule {
  type: string
  img: string
  label: string
  description: string
}

interface Member {
  avatar: string
  fullname: string
  job: string
}
